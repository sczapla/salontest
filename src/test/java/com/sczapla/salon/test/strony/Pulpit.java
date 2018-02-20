package com.sczapla.salon.test.strony;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Pulpit extends Menu {

	@FindBy(id = "index:userLinkId")
	private WebElement uzytkownicyLink;

	@FindBy(id = "index:visitKlientLinkId")
	private WebElement rezerwacjeLink;

	@FindBy(id = "index:historyLinkId")
	private WebElement historiaLink;

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
