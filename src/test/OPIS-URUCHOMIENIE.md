# Uruchomienie testów w IDE

Aby uruchomić testy w Eclipsie należy:

- zaimportować projekt z repozytorium
- wykonać na pliku pom.xml akcję Maven > Update project w celu pobrania zależności
- pobrać chromewebdriver ze strony 
  https://sites.google.com/a/chromium.org/chromedriver/downloads
  i rozpakować do dowolnego katalogu
- dodać powyższy katalog do zmiennej środowiskowej PATH
- zainstalować plugin Lombok do eclipse'a zgodnie z opisem na  
    https://projectlombok.org/setup/eclipse
- zrestartować eclipse'a


Po wykonaniu powyższych czynności kliknięcie w ikonkę Run uruchomi wszystkie testy
w projekcie.

Powodzenia!
