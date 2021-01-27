package br.com.alx.scrapper.service.impl.amazon;

import br.com.alx.scrapper.service.ScrapperService;
import br.com.alx.scrapper.service.base.AmazonBaseScrapper;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AmazonFranceScrapperImpl extends AmazonBaseScrapper implements ScrapperService {

    @Autowired
    protected AmazonFranceScrapperImpl(WebDriver ghostDriver, @Value("${sites.amazon.fr.url}") String url) {
        super(ghostDriver, url);
    }
}
