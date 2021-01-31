package br.com.alx.scrapper.service.scrapper;

import java.util.Optional;

public interface ScrapperService {

    Optional<String> getPriceSection();
    String getStoreName();

}
