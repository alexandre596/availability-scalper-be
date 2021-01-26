package br.com.alx.scrapper.service.base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;

public abstract class BaseScrapper {

    private final WebDriver ghostDriver;

    @Value("${sites.cdiscount.url}")
    private String phantomJsPath;

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseScrapper.class);

    public BaseScrapper() {
        try {
            //set binary path of phantomJS driver
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setJavascriptEnabled(true);
            //TODO read different files based on operation system using profiles
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, new ClassPathResource("/lib/phantomjs-mac").getFile().getAbsolutePath());
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, "Y");
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, Arrays.asList("--webdriver-loglevel=NONE"));
            capabilities.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:16.0) Gecko/20121026 Firefox/16.0");

            this.ghostDriver = new PhantomJSDriver(capabilities);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(); //TODO throw a specific exception and notify
        }
    }

    public Document getFullPageContent(String url) {
        LOGGER.info("Reading data from {}", url);

        try {
            ghostDriver.get(url);
            return Jsoup.parse(ghostDriver.getPageSource());
        } finally {
            ghostDriver.quit();
        }
    }


}
