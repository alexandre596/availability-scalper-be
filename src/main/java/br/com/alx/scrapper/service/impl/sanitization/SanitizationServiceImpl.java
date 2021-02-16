package br.com.alx.scrapper.service.impl.sanitization;

import br.com.alx.scrapper.service.sanitization.SanitizationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.stereotype.Service;

@Service
public class SanitizationServiceImpl implements SanitizationService {

    private final PolicyFactory policyFactory;

    public SanitizationServiceImpl() {
        this.policyFactory = Sanitizers.FORMATTING
                .and(Sanitizers.STYLES)
                .and(Sanitizers.IMAGES)
                .and(Sanitizers.LINKS);
    }

    @Override
    public String sanitize(String input) {
        return policyFactory.sanitize(input).replaceAll("(?m)^[ \t]*\r?\n", "").trim() + RandomStringUtils.randomAlphanumeric(5);
    }
}
