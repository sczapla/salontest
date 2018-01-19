package com.sczapla.salon.test.wsparcie;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.sczapla.salon.test.testy.DaneTestowe;

@Configuration
@PropertySource({
        "classpath:konfiguracja.properties",
        "classpath:danetestowe.properties"
})
@EnableConfigurationProperties(DaneTestowe.class)
class KonfiguracjaTestow implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Bean
    WebDriver getWebDriver() {
        return new ChromeDriver();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        applicationContext = appContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
