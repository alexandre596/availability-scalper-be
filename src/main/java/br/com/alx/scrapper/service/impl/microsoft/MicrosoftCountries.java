package br.com.alx.scrapper.service.impl.microsoft;

public enum MicrosoftCountries {

    AUSTRIA("de", "AT"),
    BELGIUM("fr", "BE"),
    //BULGARIA("bg", "BG"),
    //CROATIA("hr", "HR"),
    //CYPRUS("en", "CY"),
    //CZECHIA("cs", "CS"),
    DENMARK("da", "DK"),
    //ESTONIA
    FINLAND("fi", "FI"),
    FRANCE("fr", "FR"),
    GERMANY("de", "DE"),
    //GREECE
    //HUNGARY
    IRELAND("en", "IE"),
    ITALY("it", "IT"),
    //LATVIA("lv", "LV"),
    //LITHUANIA("lt", "LT"),
    //LUXEMBOURG("fr", "LU"),
    //MALTA("en", "MT"),
    NETHERLANDS("nl", "NL"),
    POLAND("pl", "PL"),
    PORTUGAL("pt", "PT"),
    //ROMANIA("ro", "RO"),
    //SLOVAKIA("sk", "SK"),
    //SLOVENIA("si", "SI"),
    SPAIN("es", "ES"),
    SWEDEN("sv", "SE"),
    ;

    private String lang;
    private String countryCode;

    MicrosoftCountries(String lang, String countryCode) {
        this.lang = lang;
        this.countryCode = countryCode;
    }

    public String getLang() {
        return lang;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
