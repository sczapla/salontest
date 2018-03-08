package com.sczapla.salon.test.strony;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.sczapla.salon.test.testy.DaneTestowe;

public class Uzytkownicy extends Strona {

	@FindBy(id = "systemUser:systemUserTable:surnameColId:filter")
	private WebElement filtrNazwisko;

	@FindBy(id = "systemUser:systemUserTable:phoneColId:filter")
	private WebElement filtrTelefon;

	@FindBy(id = "systemUser:userTabView:userFirstName")
	private WebElement uzytkownikImie;

	@FindBy(id = "systemUser:userTabView:userSurName")
	private WebElement uzytkownikNazwisko;

	@FindBy(id = "systemUser:userTabView:userPhone")
	private WebElement uzytkownikTelefon;

	@FindBy(id = "systemUser:userTabView:userPassword")
	private WebElement uzytkownikHaslo;

	@FindBy(id = "systemUser:userTabView:userMail")
	private WebElement uzytkownikEmail;

	@FindBy(id = "systemUser:userAddButton")
	private WebElement przyciskDodaj;

	@FindBy(xpath = "//*[@id=\"systemUser:userTabView\"]/ul/li[2]/a")
	private WebElement zakladkaRole;

	@FindBy(xpath = "//*[@id=\"systemUser:userTabView:rolePickList\"]/div[1]/ul/li[3]")
	private WebElement rolaKlient;

	@FindBy(xpath = "//*[@id=\"systemUser:userTabView:rolePickList\"]/div[2]/div/button[1]")
	private WebElement przyciskDodajRole;

	@FindBy(id = "systemUser:saveBtn")
	private WebElement przyciskZapiszUzytkownika;

	@FindBy(id = "systemUser:confirmYesBtn")
	private WebElement przyciskPotwierdz;

	@FindBy(id = "systemUser:systemUserTable:0:delete")
	private WebElement przyciskUsunUzytkownika;

	@FindBy(xpath = "//*[@id=\"systemUser:systemUserTable_data\"]/tr")
	private WebElement komunikat;

	@FindBy(id = "systemUser:systemUserTable:0:edit")
	private WebElement przyciskEdytujUzytkownika;

	@FindBy(xpath = "//*[@id=\"systemUser:systemUserTable_data\"]/tr/td[3]")
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
		waitForMiliseconds(1000);
		rolaKlient.click();
		waitForMiliseconds(1000);
		przyciskDodajRole.click();
		waitForMiliseconds(1000);
		przyciskZapiszUzytkownika.click();
		waitForMiliseconds(2000);
		return this;
	}

	public Uzytkownicy filtrujWgNazwiska(String nazwisko) {
		filtrNazwisko.sendKeys(nazwisko);
		waitForMiliseconds(3000);
		return this;
	}

	public Uzytkownicy filtrujWgTelefonu(String telefon) {
		filtrTelefon.sendKeys(telefon);
		waitForMiliseconds(3000);
		return this;
	}

	public Uzytkownicy usunUzytkownika() {
		przyciskUsunUzytkownika.click();
		przyciskPotwierdz.click();
		return this;
	}

	public String pobierzOpisTabeli() {
		return komunikat.getAttribute("class");
	}

	public String pobierzTelefonZTabeli() {
		return telefonUzytkownikaZTabeli.getText();
	}

	public Uzytkownicy edytujUzytkownika() {
		przyciskEdytujUzytkownika.click();
		waitForMiliseconds(2000);
		return this;
	}

	public Uzytkownicy wpiszTelefonUzytkownika(String telefon) {
		uzytkownikTelefon.clear();
		uzytkownikTelefon.sendKeys(telefon);
		waitForMiliseconds(2000);
		return this;
	}

	public Uzytkownicy zapiszUzytkownika() {
		przyciskZapiszUzytkownika.click();
		waitForMiliseconds(3000);
		return this;
	}

}
