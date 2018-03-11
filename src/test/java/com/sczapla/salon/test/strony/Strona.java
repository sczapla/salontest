package com.sczapla.salon.test.strony;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Strona {
	protected final WebDriver webDriver;

	private Strona() {
		throw new IllegalArgumentException("Strona może być tworzona tylko poprzez przekazanie WebDrivera");
	}

	protected Strona(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	protected void waitFor(ExpectedCondition<WebElement> expectedCondition) {
		new WebDriverWait(webDriver, 7).until(expectedCondition);
	}

	protected void waitForVisible(WebElement element) {
		new WebDriverWait(webDriver, 7).until(ExpectedConditions.visibilityOf(element));
	}

	protected void waitForClickable(WebElement element) {
		new WebDriverWait(webDriver, 10, 300).until(ExpectedConditions.elementToBeClickable(element));
	}

	protected void acceptAlertOptionally() {
		try {
			new WebDriverWait(webDriver, 2).until(ExpectedConditions.alertIsPresent());
			webDriver.switchTo().alert();
			webDriver.switchTo().alert().accept();
			webDriver.switchTo().defaultContent();
		} catch (TimeoutException | UnhandledAlertException e) {
			// Jeśli nie ma alteru to ignorujemy błąd
		}
	}

	protected void waitForMiliseconds(int miliSecondsToWait) {
		try {
			Thread.sleep(miliSecondsToWait);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
