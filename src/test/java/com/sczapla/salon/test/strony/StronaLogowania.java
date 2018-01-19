package com.sczapla.salon.test.strony;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StronaLogowania extends Strona {
    @FindBy(id = "username")
	private WebElement login;
    
    @FindBy(id = "password")
    private WebElement password;
    
    @FindBy(id = "btnLogin")
    private WebElement przyciskZaloguj;

    public StronaLogowania(WebDriver webDriver) {
        super(webDriver);
    }

    public void zaloguj(String uzytkownik, String haslo) {
        webDriver.manage().window().maximize();
    	login.sendKeys(uzytkownik);
        password.sendKeys(haslo);
        przyciskZaloguj.click();
    }

    public static StronaLogowania otworz(WebDriver webDriver, String bazowyUrl) {
        webDriver.get(bazowyUrl);
        return new StronaLogowania(webDriver);
    }

}
