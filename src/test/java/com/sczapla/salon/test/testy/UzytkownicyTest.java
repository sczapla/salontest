package com.sczapla.salon.test.testy;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.sczapla.salon.test.strony.Logowanie;
import com.sczapla.salon.test.wsparcie.WebTest;

@RunWith(SpringRunner.class)
@WebTest
public class UzytkownicyTest {

	@Autowired
	private WebDriver webDriver;

	@Autowired
	private DaneTestowe dane;

	@Test
	public void testDodawanieUzytkownika() {
		String klasyCss = Logowanie.otworz(webDriver, dane.getBazowyUrl())
				.zaloguj(dane.getUzytkownikKierownikLogin(), dane.getUzytkownikKierownikHaslo()).przejdzDoUzytkownikow()
				.kliknijDodajUzytkownika().wpiszDaneNowegoUzytkownika(dane)
				.filtrujWgNazwiska(dane.getNowyUzytkownikNazwisko()).usunUzytkownika()
				.filtrujWgNazwiska(dane.getNowyUzytkownikNazwisko()).pobierzOpisTabeli();

		assertEquals("ui-widget-content ui-datatable-empty-message", klasyCss);
	}

	@Test
	public void testModyfikacjaUzytkownika() {
		String telefon = String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 1000000000));
		String telefonZTabeli = Logowanie.otworz(webDriver, dane.getBazowyUrl())
				.zaloguj(dane.getUzytkownikKierownikLogin(), dane.getUzytkownikKierownikHaslo()).przejdzDoUzytkownikow()
				.filtrujWgNazwiska(dane.getIstniejacyUzytkownikNazwisko()).edytujUzytkownika()
				.wpiszTelefonUzytkownika(telefon).zapiszUzytkownika().pobierzTelefonZTabeli();
		assertEquals(telefon, telefonZTabeli);
	}

}
