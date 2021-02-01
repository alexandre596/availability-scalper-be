package br.com.alx.scrapper.service.email;

import br.com.alx.scrapper.exception.EmailNotSentException;

public interface EmailService {

    void sendSimpleMessage(String storeName, String oldContent, String newContent) throws EmailNotSentException;

}