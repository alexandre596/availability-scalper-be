package br.com.alx.scrapper;

import br.com.alx.scrapper.scrap.PageScrapperComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvailabilityScrapperBeApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(AvailabilityScrapperBeApplication.class);

	private final PageScrapperComponent pageScrapperComponent;

	@Autowired
	public AvailabilityScrapperBeApplication(PageScrapperComponent pageScrapperComponent) {
		this.pageScrapperComponent = pageScrapperComponent;
	}

	public static void main(String[] args) {
		SpringApplication.run(AvailabilityScrapperBeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		pageScrapperComponent.scrapPages();
	}
}
