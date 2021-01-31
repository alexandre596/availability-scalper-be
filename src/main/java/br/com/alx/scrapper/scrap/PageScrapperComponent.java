package br.com.alx.scrapper.scrap;

import br.com.alx.scrapper.service.ScrapperService;
import com.google.common.collect.ImmutableMap;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(PageScrapperComponent.class);
    private static final String NO_DATA = "no-data";

    private final List<ScrapperService> list;

    private Map<String, String> previousTags;
    private final Map<String, String> currentTags;

    @Autowired
    public PageScrapperComponent(List<ScrapperService> list) {
        this.list = list;

        this.currentTags = new HashMap<>();
        this.previousTags = ImmutableMap.of();
    }

    public void scrapPages() {
        while (true) {
            // Read the data from every website
            LOGGER.info("Getting ready to read information.");
            for (ScrapperService scrapperService : list) {
                this.currentTags.put(scrapperService.toString(), scrapperService.getPriceSection().orElse(NO_DATA));

                if(this.previousTags.containsKey(scrapperService.toString())) {
                    String oldValue = this.previousTags.get(scrapperService.toString());
                    String newValue = this.currentTags.get(scrapperService.toString());
                    if (!oldValue.equals(newValue)) {
                        // Compare the data with the previous loaded information
                        LOGGER.info(">>> ATTENTION: CHANGES HAVE BEEN FOUND!");
                        LOGGER.info("Difference on: {}", scrapperService);
                        this.sendNotification(oldValue, newValue);
                    }
                }
            }

            LOGGER.info("Information read successfully.");

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

    private void sendNotification(String oldValue, String newValue) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("OLD DATA: {}", oldValue);
            LOGGER.info("NEW DATA: {}", newValue);
        }

        LOGGER.info("Sending notification about changes!");
    }
}
