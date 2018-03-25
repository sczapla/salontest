package com.sczapla.salon.test.testy;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.sczapla.salon.test.strony.Logowanie;
import com.sczapla.salon.test.strony.Pulpit;
import com.sczapla.salon.test.wsparcie.WebTest;

@RunWith(SpringRunner.class)
@WebTest
public class LogowanieTest {

	@Autowired
	private WebDriver webDriver;

	@Autowired
	private DaneTestowe dane;

	@Test
	public void testLogowanie() {
		Pulpit pulpit = Logowanie.otworz(webDriver, dane.getBazowyUrl())
				.zaloguj(dane.getUzytkownikKierownikLogin(), dane.getUzytkownikKierownikHaslo());
		assertTrue(pulpit.czyWidocznyLinkUzytkownicy());

		Logowanie logowanie = pulpit.wyloguj();
	}

}
