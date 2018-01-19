package com.sczapla.salon.test.strony;

import org.openqa.selenium.By;
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

    /**
     * Czeka aż zostanie spełniony przekazany warunek. Należy przekazać jeden z ExpectedConditions.*.
     */
    protected void waitFor(ExpectedCondition<WebElement> expectedCondition) {
        new WebDriverWait(webDriver, 7).until(expectedCondition);
    }

    /**
     * Czeka aż dany element będzie widoczny dla użytkownika. Uwaga. Uznaje, że element jest widoczny nawet jeśli
     * jest zasłonięty przez przyciemnioną załonę komunikatu "Trwa pobieranie danych".
     */
    protected void waitForVisible(WebElement element) {
        new WebDriverWait(webDriver, 7).until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Czeka aż dany element będzie klikalny. Szczególnie przydatne na stronach które po wykonaniu akcji przez użytkownika
     * pokazują przyciemnioną zasłonę strony z komunikatem "Trwa pobieranie danych".
     */
    protected void waitForClickable(WebElement element) {
        new WebDriverWait(webDriver, 10, 300 ).until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Czeka 2 sekundy na pojawienie się altertu i jeśli się pojawi to go aceptuje, jeśli się nie pojawi to nie robi nic.
     */
    protected void acceptAlertOptionally() {
        try {
            new WebDriverWait(webDriver, 2).until(ExpectedConditions.alertIsPresent());
            webDriver.switchTo().alert();
            webDriver.switchTo().alert().accept();
            webDriver.switchTo().defaultContent();
        }
        catch (TimeoutException | UnhandledAlertException e) {
            // Jeśli nie ma alteru to ignorujemy błąd
        }
    }

    /**
     * Oczekiwania na zniknięcie typowej dla SERUM strony oczekiwania na pobranie strony z tekstem "Trwa pobieranie
     * danych.." i zasłoniętym tłem.
     */
    protected void waitForProgressBar() {
    	WebElement progressBar = webDriver.findElement(By.id("_uf_komunikat"));
    	new WebDriverWait(webDriver, 10, 800).until(ExpectedConditions.invisibilityOf(progressBar));
    }

}
