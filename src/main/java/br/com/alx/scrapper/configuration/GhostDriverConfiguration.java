package br.com.alx.scrapper.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Arrays;

@Configuration
public class GhostDriverConfiguration {

    @Bean(destroyMethod = "quit")
    public WebDriver ghostDriver() {
        try {
            //set binary path of phantomJS driver
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setJavascriptEnabled(true);
            //TODO read different files based on operation system using profiles
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, new ClassPathResource("/lib/phantomjs-mac").getFile().getAbsolutePath());
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_PAGE_SETTINGS_PREFIX, "Y");
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, Arrays.asList("--webdriver-loglevel=NONE"));
            capabilities.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:16.0) Gecko/20121026 Firefox/16.0");

            return new PhantomJSDriver(capabilities);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(); //TODO throw a specific exception and notify
        }
    }

}
