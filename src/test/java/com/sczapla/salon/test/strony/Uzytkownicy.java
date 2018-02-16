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
	private WebElement uzytkownikImie;

	@FindBy(id = "systemUser:j_idt50:userSurName")
	private WebElement uzytkownikNazwisko;

	@FindBy(id = "systemUser:j_idt50:userPhone")
	private WebElement uzytkownikTelefon;

	@FindBy(id = "systemUser:j_idt50:userPassword")
	private WebElement uzytkownikHaslo;

	@FindBy(id = "systemUser:j_idt50:userMail")
	private WebElement uzytkownikEmail;

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

	@FindBy(css = "#systemUser:systemUserTable_data > tr > td")
	private WebElement komunikat;

	@FindBy(id = "systemUser:systemUserTable:0:edit")
	private WebElement przyciskEdytujUzytkownika;

	@FindBy(css = "#systemUser:systemUserTable_data > tr > td:nth-child(3)")
	private WebElement telefonUzytkownikaZTabeli;

	public Uzytkownicy(WebDriver webDriver) {
		super(webDriver);
		waitForClickable(przyciskDodaj);
	}

	public Uzytkownicy kliknijDodajUzytkownika() {
		przyciskDodaj.click();
		waitForVisible(uzytkownikImie);
		return this;
	}

	public Uzytkownicy wpiszDaneNowegoUzytkownika(DaneTestowe dane) {
		uzytkownikImie.sendKeys(dane.getNowyUzytkownikImie());
		uzytkownikNazwisko.sendKeys(dane.getNowyUzytkownikNazwisko());
		uzytkownikTelefon.sendKeys(dane.getNowyUzytkownikTelefon());
		uzytkownikHaslo.sendKeys(dane.getNowyUzytkownikHaslo());
		uzytkownikEmail.sendKeys(dane.getNowyUzytkownikEmail());
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

	public Uzytkownicy filtrujWgTelefonu(String telefon) {
		filtrTelefon.sendKeys(telefon);
		waitForProgressBar();
		return this;
	}

	public Uzytkownicy usunUzytkownika() {
		przyciskUsunUzytkownika.click();
		przyciskPotwierdz.click();
		return this;
	}

	public String pobierzOpisTabeli() {
		return komunikat.getText();
	}

	public String pobierzTelefonZTabeli() {
		return telefonUzytkownikaZTabeli.getText();
	}

	public Uzytkownicy edytujUzytkownika() {
		przyciskEdytujUzytkownika.click();
		return this;
	}

	public Uzytkownicy wpiszTelefonUzytkownika(String telefon) {
		uzytkownikNazwisko.sendKeys(telefon);
		return this;
	}

	public Uzytkownicy zapiszUzytkownika() {
		przyciskZapiszUzytkownika.click();
		waitForProgressBar();
		return this;
	}

}
