package com.sczapla.salon.test.testy;

import com.sczapla.salon.test.strony.Historia;
import com.sczapla.salon.test.strony.Logowanie;
import com.sczapla.salon.test.wsparcie.WebTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebTest
public class WizytaTest {

	@Autowired
	private WebDriver webDriver;

	@Autowired
	private DaneTestowe dane;

	@Test
	public void testDodawanieUzytkownika() {
		Historia historia = Logowanie.otworz(webDriver, dane.getBazowyUrl())
				.zaloguj(dane.getUzytkownikKlient1Login(), dane.getUzytkownikKlient1Haslo())
				.przejdzDoRezerwacji().wybierzUsluge().wybierzNastepnyTydzien()
				.zarezerwujTermin().przejdzDoPulpitu().przejdzDoHistorii();

		assertEquals("ZAREZERWOWANE", historia.pobierzStatus());

		historia = historia.usunWizyte();

		assertEquals("ANULOWANE", historia.pobierzStatus());
	}

}
