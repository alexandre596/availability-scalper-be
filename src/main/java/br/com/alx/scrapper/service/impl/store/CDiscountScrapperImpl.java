package br.com.alx.scrapper.service.impl.store;

import br.com.alx.scrapper.service.ScrapperService;
import br.com.alx.scrapper.service.base.BaseScrapper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;

@Service
public class CDiscountScrapperImpl extends BaseScrapper implements ScrapperService {

    @Value("${sites.cdiscount.url}")
    private String url;

    private static final Logger LOGGER = LoggerFactory.getLogger(CDiscountScrapperImpl.class);

    @Override
    public Optional<String> getPriceSection() {
        Document page = getFullPageContent(url);
        Elements repositories = page.select("#MainZone1 > div.carousel.carouselImage.jsCarouselImage > ul > li:nth-child(1)");

        if (repositories == null || !repositories.toString().contains("Console Xbox Series X")) {
            LOGGER.warn("The item could not be found. Maybe something has changed?");
            return Optional.of("Invalid selection " + RandomStringUtils.random(5));
        } else {
            return Optional.ofNullable(repositories.toString());
        }
    }
}