package br.com.alx.scrapper.service.impl.amazon;

import br.com.alx.scrapper.service.ScrapperService;
import br.com.alx.scrapper.service.base.AmazonBaseScrapper;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AmazonGermanyScrapperImpl extends AmazonBaseScrapper implements ScrapperService {

    @Autowired
    protected AmazonGermanyScrapperImpl(WebDriver ghostDriver, @Value("${sites.amazon.de.url}") String url) {
        super(ghostDriver, url);
    }
}
