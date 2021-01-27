package br.com.alx.scrapper.service.base;

import br.com.alx.scrapper.service.ScrapperService;
import br.com.alx.scrapper.service.impl.amazon.AmazonFranceScrapperImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class AmazonBaseScrapper extends BaseScrapper implements ScrapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AmazonBaseScrapper.class);

    protected AmazonBaseScrapper(WebDriver ghostDriver, String url) {
        super(ghostDriver, url);
    }

    @Override
    public Optional<String> getPriceSection() {
        Document page = getFullPageContent(this.url);
        Element repository = page.getElementById("buybox");

        if (repository == null) {
            LOGGER.warn("The item could not be found. Maybe something has changed?");
            return Optional.of("Invalid selection " + RandomStringUtils.randomAlphanumeric(5));
        } else {
            String parsedResult = repository.toString()
                    .replaceAll("\\\"([A-Za-z0-9/+]+)==\\\"", "\"token\"");

            return Optional.of(parsedResult);
        }
    }
}
