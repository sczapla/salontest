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

	@FindBy(id = "abc:exitBt")
	private WebElement przyciskWyloguj;

	protected Pulpit(WebDriver webDriver) {
		super(webDriver);
		waitForMiliseconds(2000);
	}

	public Uzytkownicy przejdzDoUzytkownikow() {
		uzytkownicyLink.click();
		return new Uzytkownicy(webDriver);
	}

	public Rezerwacja przejdzDoRezerwacji() {
		rezerwacjeLink.click();
		return new Rezerwacja(webDriver);
	}

	public Historia przejdzDoHistorii() {
		historiaLink.click();
		return new Historia(webDriver);
	}

	public Logowanie wyloguj() {
		przyciskWyloguj.click();
		waitForMiliseconds(2000);
		return new Logowanie(webDriver);
	}

	public boolean czyWidocznyLinkUzytkownicy() {
		return uzytkownicyLink.isDisplayed();
	}
}
