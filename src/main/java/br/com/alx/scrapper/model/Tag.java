package br.com.alx.scrapper.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@EqualsAndHashCode
@ToString
@Builder
@Document
public class Tag {

    @Id
    private final String id;

    @Indexed(unique=true)
    private final String serviceName;

    @Setter
    private String tagContent;

}
