package com.sczapla.salon.test.strony;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Rezerwacja extends Menu {

	@FindBy(id = "visit:offer")
	private WebElement wyborUslugi;

	protected Rezerwacja(WebDriver webDriver) {
		super(webDriver);
		waitForVisible(wyborUslugi);
	}

}
