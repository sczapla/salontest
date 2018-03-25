package com.sczapla.salon.test.strony;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Rezerwacja extends Menu {

	@FindBy(id = "visit:offer")
	private WebElement wyborUslugi;

	@FindBy(xpath = "/html/body/div[3]/div[2]/section[2]/form/div/div/div[1]/div/div/div/table/tbody/tr/td[2]/label")
	private WebElement wyborKosmetyczka;

	@FindBy(xpath = "/html/body/div[3]/div[2]/section[2]/form/div/div/div[3]/div/div/div/div[1]/div[1]/div/button[2]")
	private WebElement nastepnyTydzien;

	@FindBy(xpath = "/html/body/div[3]/div[2]/section[2]/form/div/div/div[3]/div/div/div/div[2]"
			+ "/div/table/tbody/tr/td/div/div/div[3]/table/tbody/tr/td[2]/div/div[2]/a[1]")
	private WebElement termin;

	@FindBy(id = "visit:confirmBt")
    private WebElement potwierdzRezerwacje;

	protected Rezerwacja(WebDriver webDriver) {
		super(webDriver);
		waitForVisible(wyborUslugi);
	}

	public Rezerwacja wybierzUsluge(){
		wyborKosmetyczka.click();
		waitForMiliseconds(2000);
		return this;
	}

	public Rezerwacja wybierzNastepnyTydzien(){
		nastepnyTydzien.click();
		waitForMiliseconds(2000);
		return this;
	}

	public Rezerwacja zarezerwujTermin(){
		termin.click();
        waitForMiliseconds(1000);
        potwierdzRezerwacje.click();
		waitForMiliseconds(2000);
		return this;
	}
}
