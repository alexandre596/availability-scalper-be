package br.com.alx.scrapper.service.impl.microsoft;

import br.com.alx.scrapper.service.base.MicrosoftBaseScrapper;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MicrosoftFranceScrapperImpl extends MicrosoftBaseScrapper {

    @Autowired
    public MicrosoftFranceScrapperImpl(WebDriver ghostDriver, @Value("${sites.microsoft.url}") String url) {
        super(ghostDriver, url, MicrosoftCountries.FRANCE);
    }
}
