package br.com.alx.scrapper.service.email;

import br.com.alx.scrapper.exception.EmailNotSentException;
import br.com.alx.scrapper.model.Tag;

public interface EmailService {

    void sendSimpleMessage(String storeName, Tag oldTag, Tag newTag) throws EmailNotSentException;

}