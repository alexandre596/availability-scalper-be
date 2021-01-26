package br.com.alx.scrapper.service.base;

public abstract class MicrosoftBaseScrapper extends BaseScrapper {

    protected String availableLabel;

    protected MicrosoftBaseScrapper(String url, String availableLabel) {
        this.availableLabel = availableLabel;
    }
}
