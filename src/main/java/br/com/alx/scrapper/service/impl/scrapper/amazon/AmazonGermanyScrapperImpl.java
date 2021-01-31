package br.com.alx.scrapper.service.impl.scrapper.amazon;

import br.com.alx.scrapper.service.scrapper.ScrapperService;
import br.com.alx.scrapper.service.impl.scrapper.base.AmazonBaseScrapper;
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

    @Override
    public String getStoreName() {
        return "Amazon Germany";
    }
}
