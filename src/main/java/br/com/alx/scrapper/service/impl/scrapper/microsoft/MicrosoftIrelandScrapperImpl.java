package br.com.alx.scrapper.service.impl.scrapper.microsoft;

import br.com.alx.scrapper.service.impl.scrapper.base.MicrosoftBaseScrapper;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MicrosoftIrelandScrapperImpl extends MicrosoftBaseScrapper {

    @Autowired
    public MicrosoftIrelandScrapperImpl(WebDriver ghostDriver, @Value("${sites.microsoft.url}") String url) {
        super(ghostDriver, url, MicrosoftCountries.IRELAND);
    }
}
