package com.sczapla.salon.test.wsparcie;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("ConstantConditions")
class SeleniumTestListener extends AbstractTestExecutionListener {
    private final Logger log = Logger.getLogger(SeleniumTestListener.class.getName());

    private boolean gdyBladZatrzymaj;
    private boolean gdyBladScreenshot;
    private boolean naKoniecZatrzymaj;

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        Throwable testException = testContext.getTestException();
        if (testException != null) {
            initProperties();
            if (gdyBladScreenshot)
                wykonajZrzutEkranu(testContext);
            if (gdyBladZatrzymaj) {
                log.log(Level.SEVERE, null, testException);
                czekajNaZamknieciePrzegladarki();
            }
        }
    }

    @Override
    public void afterTestExecution(TestContext testContext) {
        initProperties();
        if (testContext.getTestException() == null)
            if (naKoniecZatrzymaj) {
                czekajNaZamknieciePrzegladarki();
            }
    }

    private void czekajNaZamknieciePrzegladarki() {
        WebDriver webDriver =  BiezacyWebDriver.get();
        try {
            new WebDriverWait(webDriver, 1800).until(ExpectedConditions.titleIs("!!!!!"));
        } catch (WebDriverException e) {
            // Ignorowanie wyjątku, który wystąpi po zamknięciu okna przeglądarki, na które
            // to zdarzenie w tej metodzie czekamy.
        }
    }

    private void wykonajZrzutEkranu(TestContext testContext) throws IOException {
        WebDriver webDriver = BiezacyWebDriver.get();
        if (webDriver instanceof TakesScreenshot) {
            File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
            String testName = toLowerUnderscore(testContext.getTestClass().getSimpleName());
            String methodName = toLowerUnderscore(testContext.getTestMethod().getName());
            new File("screenshots").mkdir();
            Files.copy(screenshot.toPath(),
                    Paths.get("screenshots", testName + "_" + methodName + "_" + screenshot.getName()));
        }
    }

    private void initProperties() {
        ApplicationContext applicationContext = KonfiguracjaTestow.getApplicationContext();
        Environment environment = applicationContext.getEnvironment();
        gdyBladScreenshot = environment.getProperty("gdy.blad.screenshot", Boolean.class);
        gdyBladZatrzymaj = environment.getProperty("gdy.blad.zatrzymaj", Boolean.class);
        naKoniecZatrzymaj = environment.getProperty("na.koniec.zatrzymaj", Boolean.class);
    }

    private static String toLowerUnderscore(String upperCamel) {
        return Stream
                .of(upperCamel.split("(?=[A-Z])"))
                .map(String::toLowerCase)
                .collect(Collectors.joining("_"));
    }
}
