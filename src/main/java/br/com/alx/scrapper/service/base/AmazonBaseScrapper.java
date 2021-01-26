package br.com.alx.scrapper.service.base;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AmazonBaseScrapper extends BaseScrapper {

    @Autowired
    public AmazonBaseScrapper(WebDriver ghostDriver) {
        super(ghostDriver);
    }
}
