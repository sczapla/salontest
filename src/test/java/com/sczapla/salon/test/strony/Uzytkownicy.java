package com.sczapla.salon.test.strony;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sczapla.salon.test.testy.DaneTestowe;

public class Uzytkownicy extends Strona {

	@FindBy(id = "systemUser:systemUserTable:j_idt39:filter")
	private WebElement filtrNazwisko;

	@FindBy(id = "systemUser:systemUserTable:j_idt41:filter")
	private WebElement filtrTelefon;

	@FindBy(id = "systemUser:j_idt50:userFirstName")
	private WebElement nowyUzytkownikImie;

	@FindBy(id = "systemUser:j_idt50:userSurName")
	private WebElement nowyUzytkownikNazwisko;

	@FindBy(id = "systemUser:j_idt50:userPhone")
	private WebElement nowyUzytkownikTelefon;

	@FindBy(id = "systemUser:j_idt50:userPassword")
	private WebElement nowyUzytkownikHaslo;

	@FindBy(id = "systemUser:j_idt50:userMail")
	private WebElement nowyUzytkownikEmail;

	@FindBy(id = "systemUser:userAddButton")
	private WebElement przyciskDodaj;

	@FindBy(linkText = "Role użytkownika")
	private WebElement zakladkaRole;

	@FindBy(xpath = "//*[@id=\"systemUser:j_idt50:rolePickList\"]/div[1]/ul/li[3]")
	private WebElement rolaKlient;

	@FindBy(xpath = "//*[@id=\"systemUser:j_idt50:rolePickList\"]/div[2]/div/button[1]")
	private WebElement przyciskDodajRole;

	@FindBy(id = "systemUser:saveBtn")
	private WebElement przyciskZapiszUzytkownika;

	@FindBy(id = "systemUser:confirmYesBtn")
	private WebElement przyciskPotwierdz;

	@FindBy(id = "systemUser:systemUserTable:0:delete")
	private WebElement przyciskUsunUzytkownika;

	public Uzytkownicy(WebDriver webDriver) {
		super(webDriver);
		waitForClickable(przyciskDodaj);
	}

	public Uzytkownicy kliknijDodajUzytkownika() {
		przyciskDodaj.click();
		waitForVisible(nowyUzytkownikImie);
		return this;
	}

	public Uzytkownicy wpiszDaneNowegoUzytkownika(DaneTestowe dane) {
		nowyUzytkownikImie.sendKeys(dane.getNowyUzytkownikImie());
		nowyUzytkownikNazwisko.sendKeys(dane.getNowyUzytkownikNazwisko());
		nowyUzytkownikTelefon.sendKeys(dane.getNowyUzytkownikTelefon());
		nowyUzytkownikHaslo.sendKeys(dane.getNowyUzytkownikHaslo());
		nowyUzytkownikEmail.sendKeys(dane.getNowyUzytkownikEmail());
		zakladkaRole.click();
		rolaKlient.click();
		przyciskDodajRole.click();
		przyciskZapiszUzytkownika.click();
		return this;
	}

	public Uzytkownicy filtrujWgNazwiska(String nazwisko) {
		filtrNazwisko.sendKeys(nazwisko);
		waitForProgressBar();
		return this;
	}

	public Uzytkownicy usunUzytkownika() {
		przyciskUsunUzytkownika.click();
		przyciskPotwierdz.click();
		return this;
	}

}
