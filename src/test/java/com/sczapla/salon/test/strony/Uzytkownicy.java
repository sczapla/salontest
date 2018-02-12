package com.sczapla.salon.test.strony;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Uzytkownicy extends Strona {
	@FindBy(id = "systemUser:systemUserTable:j_idt102:filter")
	private WebElement filtrNazwisko;

	@FindBy(id = "systemUser:systemUserTable:j_idt104:filter")
	private WebElement filtrTelefon;

	@FindBy(id = "userAddButton")
	private WebElement przyciskDodaj;

	public Uzytkownicy(WebDriver webDriver) {
		super(webDriver);
	}

}
