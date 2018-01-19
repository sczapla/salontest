package com.sczapla.salon.test.testy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.sczapla.salon.test.strony.StronaLogowania;
import com.sczapla.salon.test.wsparcie.WebTest;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@WebTest
public class LogowanieTest {

    @Autowired
    WebDriver webDriver;

    @Autowired
    DaneTestowe dane;

    @Test
    public void testLogowanie() {
    	StronaLogowania.otworz(webDriver, dane.bazowyUrl)
        .zaloguj(dane.uzytkownikKierownikLogin, dane.uzytkownikKierownikHaslo);
    }

}
