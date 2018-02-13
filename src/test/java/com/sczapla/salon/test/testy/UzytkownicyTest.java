package com.sczapla.salon.test.testy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.sczapla.salon.test.strony.Logowanie;
import com.sczapla.salon.test.strony.Uzytkownicy;
import com.sczapla.salon.test.wsparcie.WebTest;

@RunWith(SpringRunner.class)
@WebTest
public class UzytkownicyTest {

	@Autowired
	WebDriver webDriver;

	@Autowired
	DaneTestowe dane;

	@Test
	public void testDodawanieUzytkownika() {
		Uzytkownicy uzytkownicy = Logowanie.otworz(webDriver, dane.bazowyUrl)
				.zaloguj(dane.uzytkownikKierownikLogin, dane.uzytkownikKierownikHaslo).przejdzDoUzytkownikow()
				.kliknijDodajUzytkownika().wpiszDaneNowegoUzytkownika(dane)
				.filtrujWgNazwiska(dane.nowyUzytkownikNazwisko).usunUzytkownika()
				.filtrujWgNazwiska(dane.nowyUzytkownikNazwisko);

	}

}
