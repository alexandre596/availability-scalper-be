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
public class GameManiaScrapperImpl extends BaseScrapper implements ScrapperService {

    private final String url;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameManiaScrapperImpl.class);

    @Autowired
    public GameManiaScrapperImpl(WebDriver ghostDriver, @Value("${sites.gamemania.url}") String url) {
        super(ghostDriver);
        this.url = url;
    }

    @Override
    public Optional<String> getPriceSection() {
        Document page = getFullPageContent(this.url);
        Elements repositories = page.getElementsByClass("order");

        if (repositories == null || repositories.isEmpty()) {
            LOGGER.warn("The item could not be found. Maybe something has changed?");
            return Optional.of("Invalid selection " + RandomStringUtils.random(5));
        } else {
            return Optional.ofNullable(repositories.toString());
        }
    }
}