package com.sczapla.salon.test.strony;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Historia extends Strona {

	@FindBy(id = "history:historyTable")
	private WebElement tabelaHistoria;

	@FindBy(xpath = "//*[@id=\"history:historyTable_data\"]/tr[1]/td[5]")
	private WebElement komorkaStatus;

	@FindBy(id = "history:historyTable:0:delete")
	private WebElement przyciskUsun;

	@FindBy(id = "history:confirmBt")
    private WebElement przyciskPotwierdzUsuniecie;

	protected Historia(WebDriver webDriver) {
		super(webDriver);
		waitForVisible(tabelaHistoria);
	}

	public Historia usunWizyte(){
		przyciskUsun.click();
		waitForMiliseconds(1000);
		przyciskPotwierdzUsuniecie.click();
		waitForMiliseconds(2000);
	}

	public String pobierzStatus(){
	    return komorkaStatus.getText();
    }

}
