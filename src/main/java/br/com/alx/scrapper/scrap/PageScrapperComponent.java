package br.com.alx.scrapper.scrap;

import br.com.alx.scrapper.exception.EmailNotSentException;
import br.com.alx.scrapper.model.Tag;
import br.com.alx.scrapper.repository.TagRepository;
import br.com.alx.scrapper.service.email.EmailService;
import br.com.alx.scrapper.service.sanitization.SanitizationService;
import br.com.alx.scrapper.service.scrapper.ScrapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PageScrapperComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageScrapperComponent.class);
    private static final String NO_DATA = "no-data";

    private final List<ScrapperService> list;
    private final EmailService emailService;
    private final SanitizationService sanitizationService;
    private final TagRepository tagRepository;

    @Autowired
    public PageScrapperComponent(List<ScrapperService> list, EmailService emailService,
                                 SanitizationService sanitizationService, TagRepository tagRepository) {
        this.list = list;
        this.emailService = emailService;
        this.sanitizationService = sanitizationService;
        this.tagRepository = tagRepository;
    }

    public void scrapPages() {
        // Read the data from every website
        LOGGER.info("Getting ready to read information.");

        for (ScrapperService scrapperService : list) {
            Tag currentTag = Tag.builder()
                    .serviceName(scrapperService.getStoreName())
                    .tagContent(sanitizationService.sanitize(scrapperService.getPriceSection().orElse(NO_DATA)))
                    .build();

            LOGGER.debug("Searching for saved tag {}", scrapperService.getStoreName());
            Optional<Tag> oldTagOptional = this.tagRepository.findByServiceName(currentTag.getServiceName());

            if (oldTagOptional.isPresent()) {
                LOGGER.debug("There are some records with the store {}", scrapperService.getStoreName());

                String oldValue = oldTagOptional.get().getTagContent();
                String newValue = currentTag.getTagContent();

                if (!oldValue.equals(newValue)) {
                    // Compare the data with the previous loaded information
                    LOGGER.info(">>> ATTENTION: CHANGES HAVE BEEN FOUND!");
                    LOGGER.info("Difference on: {}", scrapperService.getStoreName());
                    this.sendNotification(scrapperService.getStoreName(), oldTagOptional.get(), currentTag);
                }
            }

            LOGGER.debug("Saving store {} in the db", scrapperService.getStoreName());

            Tag tagToSave = oldTagOptional.orElse(currentTag);
            tagToSave.setTagContent(currentTag.getTagContent());
            this.tagRepository.save(tagToSave);
        }

        LOGGER.info("Information read successfully.");
    }

    private void sendNotification(String storeName, Tag oldTag, Tag newTag) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("OLD DATA: {}", oldTag.getTagContent());
            LOGGER.debug("NEW DATA: {}", newTag.getTagContent());
        }

        try {
            emailService.sendSimpleMessage(storeName, oldTag, newTag);
        } catch (EmailNotSentException e) {
            //TODO create some sort of messaging system in case it fails to send the email.
            LOGGER.error("Failed to send email.");
        }
        LOGGER.info("Sending notification about changes!");
    }
}
