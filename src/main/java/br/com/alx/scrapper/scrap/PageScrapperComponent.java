package br.com.alx.scrapper.scrap;

import br.com.alx.scrapper.service.ScrapperService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class PageScrapperComponent {

    private static Logger LOGGER = LoggerFactory.getLogger(PageScrapperComponent.class);

    private final List<ScrapperService> list;

    private Map<String, String> previousTags;
    private final Map<String, String> currentTags;

    @Autowired
    public PageScrapperComponent(List<ScrapperService> list) {
        this.list = list;

        this.currentTags = new HashMap<>();
    }

    public void scrapPages() {
        while (true) {
            // Read the data from every website
            LOGGER.info("Getting ready to read information.");
            for (ScrapperService scrapperService : list) {
                this.currentTags.put(scrapperService.toString(), scrapperService.getPriceSection().orElse("no-data"));
            }

            LOGGER.info("Information read successfully.");

            // Compare the data with the previous loaded information
            if (MapUtils.isNotEmpty(this.previousTags)) {
                LOGGER.info("Comparing current data with previous loaded information.");
                MapDifference<String, String> diff = Maps.difference(this.currentTags, this.previousTags);

                if (!diff.areEqual()) {
                    LOGGER.info(">>> ATTENTION: CHANGES HAVE BEEN FOUND!");
                    this.sendNotification(diff.entriesDiffering());
                }
            }

            // Save the current data to compare in the future.
            this.previousTags = ImmutableMap.copyOf(this.currentTags);

            try {
                // sleep 5 minutes
                LOGGER.info("Reading complete. Sleeping for 5 minutes");

                // TODO: schedule the run? how to save the data? possibly a db?
                TimeUnit.MINUTES.sleep(5);

                // In case of any IO errors, we want the messages written to the console
            } catch (InterruptedException e) {
                //TODO
                e.printStackTrace();
            }
        }
    }

    private void sendNotification(Map<String, MapDifference.ValueDifference<String>> entriesDiffering) {
        LOGGER.info("Sending notification about changes!");
    }
}
