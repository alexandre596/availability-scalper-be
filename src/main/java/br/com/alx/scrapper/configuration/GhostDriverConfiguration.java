package br.com.alx.scrapper.configuration;

import br.com.alx.scrapper.exception.PhantomJsFileNotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class GhostDriverConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(GhostDriverConfiguration.class);

    @Bean(destroyMethod = "quit")
    public WebDriver ghostDriver(@Value("${phantom.js.file.location}") String phantomJsFileLocation) {
        try {
            //set binary path of phantomJS driver
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setJavascriptEnabled(true);
            //TODO read different files based on operation system using profiles
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, new ClassPathResource(phantomJsFileLocation).getFile().getAbsolutePath());
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, "Y");
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, Collections.singletonList("--webdriver-loglevel=NONE"));
            capabilities.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:16.0) Gecko/20121026 Firefox/16.0");

            return new PhantomJSDriver(capabilities);
        } catch (IOException e) {
            LOGGER.error("Could not load phantomjs file", e);
            throw new PhantomJsFileNotFoundException(e);
        }
    }

}
