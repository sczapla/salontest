package com.sczapla.salon.test.strony;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Pulpit extends Strona {

	@FindBy(id = "index:userLinkId")
	private WebElement uzytkownicyLink;

	protected Pulpit(WebDriver webDriver) {
		super(webDriver);
		waitForClickable(uzytkownicyLink);
	}

	public Uzytkownicy przejdzDoUzytkownikow() {
		uzytkownicyLink.click();
		return new Uzytkownicy(webDriver);
	}

	public boolean czyWidocznyLinkUzytkownicy() {
		return uzytkownicyLink.isDisplayed();
	}
}
