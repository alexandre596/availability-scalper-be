package br.com.alx.scrapper.service.base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseScrapper {

    private final WebDriver ghostDriver;
    protected final String url;

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseScrapper.class);

    @Autowired
    public BaseScrapper(WebDriver ghostDriver, String url) {
        super();
        this.ghostDriver = ghostDriver;
        this.url = url;
    }

    public Document getFullPageContent(String url) {
        LOGGER.info("Reading data from {}", url);

        ghostDriver.get(url);
        return Jsoup.parse(ghostDriver.getPageSource());
    }


}
