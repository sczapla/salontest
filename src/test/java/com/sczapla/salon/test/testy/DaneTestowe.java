package com.sczapla.salon.test.testy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * Klasa umożliwiająca uzyskanie dostępu z testów do danych znajdujących się w plikach properties
 * danetestowe.properties. Uwzględnia przełączony profil tj. pliki danetestowe-PROFIL.properties.
 *
 * Aby dodac obsługę nowego propertiesa wystarczy dodac w tej klasie pole odpowiadajce nazwie propertiesa. Nie musi
 * to być zgodność dokładna np. "bazowyUrl" może być zapisany w pliku propeties jako "bazowy-url" lub "bazowy_url".
 *
 * @author mstalmach
 */
@ConfigurationProperties()
@Getter
@Setter
public class DaneTestowe {
	String uzytkownikKierownikLogin;
    String uzytkownikKierownikHaslo;
    String bazowyUrl;
}
