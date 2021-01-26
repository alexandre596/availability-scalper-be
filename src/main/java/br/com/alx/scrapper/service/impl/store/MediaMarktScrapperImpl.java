package br.com.alx.scrapper.service.impl.store;

import br.com.alx.scrapper.service.ScrapperService;
import br.com.alx.scrapper.service.base.BaseScrapper;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class MediaMarktScrapperImpl extends BaseScrapper implements ScrapperService {

    @Value("${sites.mediamarkt.url}")
    private String url;

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaMarktScrapperImpl.class);

    @Override
    public Optional<String> getPriceSection() {
        Document page = getFullPageContent(url);
        Elements repositories = page.getElementsByClass("price-sidebar");

        if (repositories == null || repositories.isEmpty()) {
            LOGGER.debug("The sidebar could not be found. Probably this item isn't available at the moment.");
        } else {
            return Optional.ofNullable(repositories.toString());
        }

        return Optional.empty();
    }
}
