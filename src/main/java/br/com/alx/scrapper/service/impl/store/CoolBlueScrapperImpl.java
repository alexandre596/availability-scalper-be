package br.com.alx.scrapper.service.impl.store;

import br.com.alx.scrapper.service.ScrapperService;
import br.com.alx.scrapper.service.base.BaseScrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoolBlueScrapperImpl extends BaseScrapper implements ScrapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoolBlueScrapperImpl.class);

    @Autowired
    public CoolBlueScrapperImpl(WebDriver ghostDriver, @Value("${sites.coolblue.url}") String url) {
        super(ghostDriver, url);
    }

    @Override
    public Optional<String> getPriceSection() {
        Document page = getFullPageContent(this.url);
        Elements repositories = page.select("#main-content > div.grid-section-xs--gap-4.grid-section-m--gap-5.grid-container-xs--gap-4--y.grid-container-m--gap-0--y.grid-container-l--gap-7--y > div:nth-child(1) > div > div.grid-unit-xs--col-12.grid-unit-m--col-6.grid-unit-xl--col-5.js-sticky-bar-trigger > div");

        if (repositories == null || repositories.isEmpty()) {
            LOGGER.warn("The item could not be found. Maybe something has changed?");
            return Optional.of("Invalid selection " + RandomStringUtils.randomAlphanumeric(5));
        } else {
            String parsedResult = repositories.toString()
                    .replaceAll("collection-item-([A-Za-z0-9]+)", "collection-item")
                    .replaceAll("data-overlay-trigger-id-([A-Za-z0-9]+)", "data-overlay-trigger-id");

            return Optional.of(parsedResult);
        }
    }
}