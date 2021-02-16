package br.com.alx.scrapper.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.Nonnull;

@Configuration
@EnableMongoRepositories(basePackages = "br.com.alx.scrapper.repository")
public class MongoConfiguration extends AbstractMongoClientConfiguration {

    private final String tableName;
    private final String connectionUrl;

    public MongoConfiguration(@Value("${database.mongodb.table.name}") String tableName,
                              @Value("${database.mongodb.connection.url}") String connectionUrl) {
        this.tableName = tableName;
        this.connectionUrl = connectionUrl + tableName;
    }

    @Override
    @Nonnull
    protected String getDatabaseName() {
        return this.tableName;
    }

    @Override
    @Nonnull
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString(this.connectionUrl);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

}
