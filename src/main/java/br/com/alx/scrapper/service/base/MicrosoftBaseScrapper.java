package br.com.alx.scrapper.service.base;

import br.com.alx.scrapper.service.ScrapperService;
import br.com.alx.scrapper.service.impl.microsoft.MicrosoftCountries;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class MicrosoftBaseScrapper extends BaseScrapper implements ScrapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MicrosoftBaseScrapper.class);

    protected MicrosoftBaseScrapper(WebDriver ghostDriver, String url) {
        super(ghostDriver, url);
    }

    protected MicrosoftBaseScrapper(WebDriver ghostDriver, String url, MicrosoftCountries microsoftCountry) {
        super(ghostDriver, url.replace("{lang}", microsoftCountry.getLang()).replace("{country}", microsoftCountry.getCountryCode()));
    }

    @Override
    public Optional<String> getPriceSection() {
        Document page = getFullPageContent(url);
        Elements repositories = page.select("#BodyContent > section > div > div");

        if (repositories == null || repositories.isEmpty()) {
            LOGGER.warn("The item could not be found. Maybe something has changed?");
            return Optional.of("Invalid selection " + RandomStringUtils.randomAlphanumeric(5));
        } else {
            return Optional.ofNullable(repositories.toString());
        }

    }
}
