# Testowanie Web

# Uruchomienie testów w IDE

Aby uruchomić testy w Eclipsie należy:

- zaimportować projekt z repozytorium
- wykonać na projekcie akcję Run as / Maven / Build 
- wykonać na projekcie akcję Maven > Update project w celu pobrania zależności
- pobrać chromewebdriver ze strony 
  https://sites.google.com/a/chromium.org/chromedriver/downloads
  i rozpakować do dowolnego katalogu
- dodać powyższy katalog do zmiennej środowiskowej PATH
- zainstalować plugin Lombok do eclipse'a zgodnie z opisem na  
    https://projectlombok.org/setup/eclipse
- zrestartować eclipse'a


Po wykonaniu powyższych czynności kliknięcie w ikonkę Run uruchomi wszystkie testy
w projekcie.

# Zasady tworzenia testów

## Testy

Testy uruchamiane są z użyciem JUnit. Każda klasa testu powinna posiadać adnotacje 
jak w przykładzie:

    @RunWith(SpringRunner.class)
    @WebTest
    public class PacjenciTest {

Klasa testu automatycznie uzyskuje dostęp do dwóch obiektów:

    @Autowired
    WebDriver webDriver;

    @Autowired
    DaneTestowe dane;

WebDriver umożliwia sterowanie przeglądarką WWW, a DaneTestowe odczytanie danych testowych (szczegółowy
opis dostępu do danych testowych znajduje się w kolejnym rozdziale).

Metody testujące do implementacji testów nie wykorzystują bezpośrednio WebDriver, ale korzystają
z obiektów stron. Obiekty te reprezentują poszczególne strony aplikacji i udostępniają metody,
umożliwiające wykonanie przez test operacji, jakie może na tych stronach wykonać użytkownik (szczegółowy
opis tworzenia obiektów stron znajduje się kolejnym rozdziale).

Przykładowy fragment testu wykorzystujący obiekty stron, symulujący wykonanie przez użytkownika kilku operacji
 wygląda następująco:


        StronaGlowna.otworz(webDriver, dane.bazowyUrl)
                .zaloguj(dane.uzytkownikMpLogin, dane.uzytkownikMpHaslo)
                .kliknijMenuPacjenci()
                .wpiszWyszukiwanyPacjent(nazwisko)
                .kliknijFiltruj();

Kolejne linki tego fragmentu testu reprezentują operacje do wykonania na kolejnych stronach np.
wywołanie `.zaloguj()` zwraca obiekt strony `StronaPoZalogowaniu`, która z kolei udostępnia
operację `kliknijMenuPacjenci`.

W metodach testujących umieszczamy najczęściej trzy sekcje kodu:
  1) przygotowanie 
  2) wykonanie właściwej akcji
  3) sprawdzanie wyniku.
  

        // 1. Przygotowanie
    	StronaGlowna.otworz(webDriver, dane.bazowyUrl)
                .zaloguj(dane.uzytkownikMpLogin, dane.uzytkownikMpHaslo)
                .kliknijMenuPacjenci()
                .kliknijDodajPacjenta()
                .wpiszImie(imie)
                .wpiszNazwisko(nazwisko)
                .wpiszNrDomu("1")
                .wpiszMiejscowosc("bytom")
                .wpiszDataUrDzien("1")
                .wpiszDataUrMiesiac("1")
                .wpiszDataUrRok("1990")
                .kliknijZapisz()
                .kliknijZamknij();

        // 2. Wykonanie akcji
        StronaPacjenci stronaPacjenci = StronaGlowna.otworz(webDriver, dane.bazowyUrl)
                .zaloguj(dane.uzytkownikMpLogin, dane.uzytkownikMpHaslo)
                .kliknijMenuPacjenci()
                .wpiszWyszukiwanyPacjent(nazwisko)
                .kliknijFiltruj();

        // 3. Sprawdzenie wyniku
        assertThat(stronaPacjenci.getDanePacjenta()).contains(nazwisko);
        assertThat(stronaPacjenci.getDanePacjenta()).contains(imie);

Sprawdzenie rezultatu wykonujemy z użyciem funkcji assertThat z biblioteki AssertJ, która w zależności
od sprawdzanego typu udostępnia dostosowane do tego typu asercje.

W przypadku gdy wiele metod testowych potrzebuje przygotowania środowiska w ten sam sposób należy
skorzystać z adnotacji @Before np. 

        @Before
        public void przygotuj()
            strona = StronaGlowna.otworz(webDriver, dane.bazowyUrl)
                        .zaloguj(dane.uzytkownikMpLogin, dane.uzytkownikMpHaslo)
        }

## Obiekty stron 

Obiekty stron reprezentują poszczególne strony aplikacji. Testy wykorzystują obiekty stron
do realizacji scenariuszy testowych.

Poszczególne metody publiczne obiektów stron reprezentują operacje jakie można wykonać na
danej stronie np.

    public StronaDodajPacjenta wpiszMiejscowosc(String miejscowosc) {
        this.miejscowosc.sendKeys(miejscowosc);
        return this;
    }

    public StronaDodajPacjenta kliknijZapisz() {
        przyciskZapisz.click();
        acceptAlertOptionally();
        return this;
    }
    
Obiekty stron powinny ukrywać przed testami szczegóły implementacyjne dotyczące
strony. Celem tworzenia obiektów stron jest m.in. to, aby w przypadku zmiany struktury strony
nie trzeba było zmieniać niczego w testach (czy w ogóle przeglądać, które testy dotyczą
których stron) -- tylko zmienić implementacje danego obiektu strony.

Obiekty stron nie powinny udostępniać na zewnątrz obiektów WebElement - powinny udostępniać operacje
działające na tych elementach. Testy nie operują na obiektach WebElement, ale tylko i wyłącznie
na metodach jakie udostępniają strony.

Wszystkie strony powinny dziedziczyć z klasy Strona i powinny posiadać konstruktor publiczny
akceptujący jeden parametr typu WebDriver.


     public class StronaGlowna extends Strona {
        public StronaGlowna(WebDriver webDriver) {
            super(webDriver);
        }
        ..
      }


Wszystkie dane testowe (nazwa użytkownika itp) potrzebne przez stronę powinny być przekazane
 do niej z testu poprzez wywołania metod. Odpowiedzialnością strony nie powinno być pobieranie
danych testowych, to test powinien przekazać wprost dane testowe. Przykładowe wywołanie obiektu
strony z testu:

        StronaPacjenci stronaPacjenci = StronaGlowna.otworz(webDriver, bazowyUrl)
                .zaloguj(login, haslo)
                .kliknijMenuPacjenci()
                .wpiszWyszukiwanyPacjent(nazwisko)
                .kliknijFiltruj();
                
Nazwy metod powinny jednoznacznie wskazywać jakie akcje zostaną wykonane. Zakłada sie stosowanie
następującego nazewnictwa:
 - kliknij* - w przypadku gdy metoda ma za zadanie naciśnięcie wybranego przycisku np. 
   kliknijZapiszSkierowanie()
 - wpisz* - w przypadku gdy do wybranego pola ma zostać wpisane kilka wartości (przekazywane 
   w tym momencie w postaci obiektu) lub jedna wartość, odpowiednio np.: wpiszSkierowanie(Skierowanie 
   skierowanie), wpiszPacjentaDlaKtoregoSkierowanie(String nazwisko)  
   
Zaleca się aby metody udostępniane przez obiekty stron realizowały pojedynczą akcje wykonywaną przez użytkownika,
w przeciwieństwie do realizowanie w jednej metodzie wielu pojedynczych akcji realizujących określony cel.
Wykonanie poszczególnych akcji w określonym celu to odpowiedzialność testu, a nie obiektu strony. Wyjatkiem
od tej reguły jest wpisanie przez użytkownika danych do wielu pól z jednego przekazanego obiektu zawierającego
te dane w polach.
      
Dostęp do elementu strony powinien być realizowany poprzez zadeklarowanie pól z adnotacją
FindBy


        public class StronaPacjenci extends StronaZNalowkiemIMenu {
            @FindBy(linkText = "Dodaj pacjenta")
            WebElement dodajPacjentaButton;

            @FindBy(id = "wzorzec")
            WebElement wzorzecPacjent;

Metody w obiektach stron, które reprezentują działania użytkownika na stronie np. kliknięcie,
wpisanie czegoś itp. powinny zwracać zawsze obiekt strony:
 - W przypadku gdy kliknięcie w dany element powoduje przejście do innej strony  
   metoda powinna zwracać stronę do której nastąpiło przejśćie
 - W przypadku gdy wywołanie danej metody nie powoduje przejścia do następnej
   strony zwrócić należy tą samą stronę (albo zwracając this -- jeśli strona
   nie została zmieniona w wyniku tej akcji, albo new StronaX(webdriver), aby zwrócić 
   stronę na nowo)
   
   
Na przykład w przypadku operacji nie wymagającej odświeżenia strony ani nawigacji:

        public StronaDodajPacjenta wpiszMiejscowosc(String miejscowosc) {
            this.miejscowosc.sendKeys(miejscowosc);
            return this;
        }


W przypadku operacji powodującej przejście do innej strony:

     public class StronaDodajPacjenta extends Strona {
        public StronaPacjenci kliknijZapiszIWybierz() {
            przyciskZapiszIWybierz.click();
            return new StronaPacjenci(webDriver);
        }
      }

W przypadku operacji powodującej odświeżenie strony:

        public StronaPacjenci kliknijFiltruj() {
            przyciskFiltruj.click();
            return new StronaPacjenci(webDriver);
        }
      
Strony odpowiedzialne są także za oczekiwanie na załadowanie elementów. Implementacja oczekiwania
konieczna jest w przypadku gdy fragmenty stron doładowane są w tle - wywołaniami AJAX. Oczekiwania
na załadowanie elementów należy wykonywać albo w konstruktorze strony -- aby sprawdzić
czy strona jest załadowana i dopiero po jej załadowaniu udostępniać operacja wykonywane 
na tej stronie (w tym przypadku najczęściej nie trzeba już wykonywać oczekiwania na elementy w 
poszczególnych metodach), albo poprzez oczekiwanie w poszczególnych metodach -- tylko na
elementy, które dotyczą tej metody. Np:

        public class StronaPacjenci extends StronaZNalowkiemIMenu {
            @FindBy(id = "wzorzec")
            WebElement wzorzecPacjent;

            public StronaPacjenci(WebDriver webDriver) {
                super(webDriver);
                waitForClickable(wzorzecPacjent);
            }
        }

W klasie bazowej Strona dostępnych jest kilka metod pomocniczych wait*, ułatwiających oczekiwanie
dla najczęstszych przypadków.

Odpowiedzialnością strony nie jest wykonywanie sprawdzeń dotyczących danych na stronie, strony
powinny tylko udostępniać dane, za których sprawdzenie odpowiedzialne są testy. Jak w poniższym 
przykładzie metoda getDanePacjenta:

     public class StronaPacjenci extends StronaZNalowkiemIMenu {
        public String getDanePacjenta() {
            return danePacjenta.stream().map(WebElement::getText).collect(Collectors.joining());
        }
      }

     @Test
     public void testDodaniaPacjenta() {
        StronaPacjenci stronaPacjenci = StronaGlowna.otworz(webDriver, bazowyUrl)
                .zaloguj(login, haslo)
                .kliknijMenuPacjenci()
                .wpiszWyszukiwanyPacjent(nazwisko)
                .kliknijFiltruj();

        assertThat(stronaPacjenci.getDanePacjenta()).contains(nazwisko);
     }

## Dane testowe

Dane testowe które są zmienne w zależności od środowiska uruchamiania tj np. nazwy użytkowników, hasła,
adres stron id. należy umieszczać w plikach danetestowe*.properties. Przykładowa zawartość pliku
danetestowe.properties:

    bazowyUrl=https://www.serum.com.pl/
    uzytkownik-mp-login=testowy2
    uzytkownik-mp-haslo=Testowy222

W pliku danetestowe-NAZWAPROFILU.properties należy umieszczać wartości specyficzne dla danego środowiska,
np. w pliku danetestowe-edu.properties, dane testowe dla środowiska edu.

W pliku danetestowe.properties (bez nazwy profilu), należy umieścić wartości, które mają być brane wtedy
kiedy nie zdefiniowano wartości specyficznych dla danego środowiska.

Uruchamiając test można określić z jakim profilem ma uruchomiony na dwa sposoby:
  - poprzez przekazanie parametru do uruchomienia java'y `-Dspring.profiles.active=NAZWA_PROFILU`
  - albo poprzez ustawienie w pliku konfiguracja.properties wartości `spring.profiles.active=NAZWA_PROFILU`
  
Aby skorzystać w teście z danych testowych, wystarczy zdefiniować pole @Autowired typu DaneTestowe i 
 z tego obiektu odczytać wartości odpowiadające polom z danych testowych np.

    @Autowired
    DaneTestowe dane;

    @Test
    public void testDodaniaPacjenta() {
    	StronaGlowna.otworz(webDriver, dane.bazowyUrl)
                .zaloguj(dane.uzytkownikMpLogin, dane.uzytkownikMpHaslo)
                .kliknijMenuPacjenci()

Dodając nową wartość do danetestowe*.properties należy dodać odpowiadające jej pole w klasie DaneTestowe. 

Możliwe jest przypisanie wartości typów prostych typu Integer, String, ale również obiektów zgodnie z 
przykładem:

    // danetestowe.properties

    bazowyUrl=https://www.serum.com.pl/
    uzytkownik-mp-login=testowy2
    uzytkownik-mp-haslo=Testowy222

    iloscPacjentw=12

    pacjent1.imie=Adam
    pacjent1.nazwisko=Testowy

    // DaneTestowe.java
    
    @ConfigurationProperties()
    @Getter
    @Setter
    public class DaneTestowe {
        String uzytkownikMpLogin;
        String uzytkownikMpHaslo;
        String bazowyUrl;

        Integer iloscPacjentow;
        
        Pacjent pacjent1 = new Pacjent();

        String kodJednostki;

        @Getter
        @Setter
        class Pacjent {
            String imie;
            String nazwisko;
        }
    }
# uruchamianie testów z poziomu wiersza poleceń wraz z generowaniem raportu

    Aby uruchomić test z poziomu wiersza poleceń nalezy zastosować następującą konstukcję:
    
    mvn surefire:test -Dtest=<nazwa_testu> surefire-report:report
    mvn surefire:test -Dtest=WizytaTest surefire-report:report
    
    
    Patrz:
    http://maven.apache.org/plugins-archives/maven-surefire-plugin-2.12.4/examples/single-test.html
    

