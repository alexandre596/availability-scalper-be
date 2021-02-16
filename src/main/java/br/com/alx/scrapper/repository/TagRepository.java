package br.com.alx.scrapper.repository;

import br.com.alx.scrapper.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TagRepository extends MongoRepository<Tag, String> {

    Optional<Tag> findByServiceName(String serviceName);

}
