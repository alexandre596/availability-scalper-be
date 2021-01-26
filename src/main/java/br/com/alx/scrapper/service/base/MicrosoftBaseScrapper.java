package br.com.alx.scrapper.service.base;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class MicrosoftBaseScrapper extends BaseScrapper {

    protected String availableLabel;

    @Autowired
    public MicrosoftBaseScrapper(WebDriver ghostDriver) {
        super(ghostDriver);
    }
}
