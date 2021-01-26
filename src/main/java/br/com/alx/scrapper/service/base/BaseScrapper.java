package br.com.alx.scrapper.service.base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class BaseScrapper {

    private final WebDriver ghostDriver;

    @Value("${sites.cdiscount.url}")
    private String phantomJsPath;

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseScrapper.class);

    @Autowired
    public BaseScrapper(WebDriver ghostDriver) {
        super();
        this.ghostDriver = ghostDriver;
    }

    public Document getFullPageContent(String url) {
        LOGGER.info("Reading data from {}", url);

        ghostDriver.get(url);
        return Jsoup.parse(ghostDriver.getPageSource());
    }


}
