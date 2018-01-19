package com.sczapla.salon.test.wsparcie;

import org.openqa.selenium.WebDriver;

class BiezacyWebDriver {
    public static WebDriver get() {
        return KonfiguracjaTestow.getApplicationContext().getBean(WebDriver.class);
    }
}
