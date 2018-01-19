# Strony 

Obiekty stron reprezentuja poszczególne strony aplikacji. Testy wykorzystują obiekty stron
do realizacji senariuszy testowych.

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


Wszystkie dane testowe (nazwa użytkownika itp) potrzebne przez strone powinny być przekane
 do niej z testu poprzez wywołania metod. Odpowiedzialnością strony nie powinno być pobieranie
danych testowych, to test powinnien przekazać wprost dane testowe. Przykładowe wywołanie obiektu
strony z testu:

        StronaPacjenci stronaPacjenci = StronaGlowna.otworz(webDriver, bazowyUrl)
                .zaloguj(login, haslo)
                .kliknijMenuPacjenci()
                .wpiszWyszukiwanyPacjent(nazwisko)
                .kliknijFiltruj();
                
                
Dostęp do elementu strony powinien być realizowany poprzez zadeklarowanie pól z adnotacją
FindBy


        public class StronaPacjenci extends StronaZNalowkiemIMenu {
            @FindBy(linkText = "Dodaj pacjenta")
            WebElement dodajPacjentaButton;

            @FindBy(id = "wzorzec")
            WebElement wzorzecPacjent;

Metody w obiektach stron, które reprezentują działania użytkownika na stronie np. kliknięcie,
wspisanie czegoś itp powinny zwracać zawsze obiekt strony:
 - W przypadku gdy klinięcie w dany element powowuje przejście do innej strony  
   metoda powinna zwracać stronę do której nastąpiło przejśćie
 - W przypadku gdy wywołanie danej metody nie powoduje przejścia do następnej
   strony zwrócić należy tą samą stronę (albo zwracając this -- jeśli strona
   nie została zmieniona w wyniku tej akcji, albo new StronaX(webdriver), aby zwróciłć 
   stronę na nowo)
   
   
Na przykład w przypadku operacji ne wymagajacej odświeżenia strony ani nawigacji:

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
na tej stronie (w tym przpadku najczęściej nie trzeba już wykonywać oczekiwania na elementy w 
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

