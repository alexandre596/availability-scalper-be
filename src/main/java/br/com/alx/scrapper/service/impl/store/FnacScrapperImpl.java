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
public class FnacScrapperImpl extends BaseScrapper implements ScrapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FnacScrapperImpl.class);

    @Autowired
    public FnacScrapperImpl(WebDriver ghostDriver, @Value("${sites.fnac.url}") String url) {
        super(ghostDriver, url);
    }

    @Override
    public Optional<String> getPriceSection() {
        Document page = getFullPageContent(this.url);
        Elements repositories = page.select("body > div.Main.Main--fullWidth > div > div.productPageTop > div.f-productPage-colRight.clearfix > section");

        if (repositories == null) {
            LOGGER.warn("The item could not be found. Maybe something has changed?");
            return Optional.of("Invalid selection " + RandomStringUtils.randomAlphanumeric(5));
        } else {
            return Optional.ofNullable(repositories.toString());
        }
    }
}
