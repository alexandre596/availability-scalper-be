package br.com.alx.scrapper.service.impl.store;

import br.com.alx.scrapper.service.ScrapperService;
import br.com.alx.scrapper.service.base.BaseScrapper;
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
public class BolScrapperImpl extends BaseScrapper implements ScrapperService {

    private final String url;
    private static final Logger LOGGER = LoggerFactory.getLogger(BolScrapperImpl.class);

    @Autowired
    public BolScrapperImpl(WebDriver ghostDriver, @Value("${sites.bol.url}") String url) {
        super(ghostDriver);
        this.url = url;
    }

    @Override
    public Optional<String> getPriceSection() {
        Document page = getFullPageContent(this.url);
        Elements repositories = page.getElementsByClass("price-block__price");

        if (repositories == null || repositories.isEmpty()) {
            LOGGER.debug("The sidebar could not be found. Probably this item isn't available at the moment.");
        } else {
            return Optional.ofNullable(repositories.toString());
        }

        return Optional.empty();
    }
}