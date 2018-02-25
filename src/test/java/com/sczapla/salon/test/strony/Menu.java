package com.sczapla.salon.test.strony;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Menu extends Strona {

	@FindBy(xpath = "//span[contains(text(),'Pulpit')]")
	private WebElement pulpit;

	@FindBy(xpath = "//a[@href='/pages/visit.xhtml']")
	private WebElement rezerwacje;

	@FindBy(xpath = "//a[@href='/pages/history.xhtml']")
	private WebElement historia;

	protected Menu(WebDriver webDriver) {
		super(webDriver);
	}

	public Pulpit przejdzDoPulpitu() {
		pulpit.click();
		return new Pulpit(webDriver);
	}

	public Rezerwacja przejdzDoRezerwacjiMenu() {
		pulpit.click();
		return new Rezerwacja(webDriver);
	}

	public Historia przejdzDoHistoriiMenu() {
		pulpit.click();
		return new Historia(webDriver);
	}

}
