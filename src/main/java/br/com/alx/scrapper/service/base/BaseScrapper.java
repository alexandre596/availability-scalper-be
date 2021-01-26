package br.com.alx.scrapper.service.base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class BaseScrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseScrapper.class);

    public Document getFullPageContent(String url) throws IOException {
        LOGGER.info("Reading data from {}", url);

        // Here we create a document object and use JSoup to fetch the website
        return Jsoup.connect(url).get();
    }


}
