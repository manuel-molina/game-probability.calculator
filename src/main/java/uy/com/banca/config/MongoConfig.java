package uy.com.banca.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoRepositories(basePackages = "uy.com.banca.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Autowired
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return env.getProperty("mongodb.database");
    }

    @Override
    public MongoClient mongoClient() {
        String cType    = env.getProperty("connection.type");
        String host     = env.getProperty("mongodb.host");
        String port     = env.getProperty("mongodb.port");
        String database = env.getProperty("mongodb.database");
        String userName = env.getProperty("mongodb.user");
        String pwd      = env.getProperty("mongodb.pwd");
        String options  = env.getProperty("mongodb.options");

        StringBuilder uriBuilder = new StringBuilder();
        if (cType != null && cType.equals("standard")) {
            uriBuilder.append("mongodb://");
            if (userName != null && !userName.isEmpty()) {
                uriBuilder.append(userName);
                if (pwd != null && !pwd.isEmpty()) {
                    uriBuilder.append(":").append(pwd);
                }
                uriBuilder.append("@");
            }
            uriBuilder.append(host);

            if (port != null && !port.isEmpty()) {
                uriBuilder.append(":").append(port);
            }

            uriBuilder.append("/").append(database);

            if (options!= null && !options.isEmpty()) {
                uriBuilder.append("?").append(options);
            }
        } else {
            uriBuilder.append("mongodb+srv://");
            if (userName != null && !userName.isEmpty()) {
                uriBuilder.append(userName);
                if (pwd != null && !pwd.isEmpty()) {
                    uriBuilder.append(":").append(pwd);
                }
                uriBuilder.append("@");
            }
            uriBuilder.append(host);

//        if (!port.isEmpty()) {
//            uriBuilder.append(":").append(port);
//        }

            uriBuilder.append("/").append(database);

            if (options != null && !options.isEmpty()) {
                uriBuilder.append("?").append(options);
            }
        }
//        mongodb+srv://mmolinaDBMongo:AtlasDB123@sandbox.y1p83.mongodb.net/myFirstDatabase?retryWrites=true&w=majority
        ConnectionString connectionString = new ConnectionString(uriBuilder.toString());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    public Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.banca");
    }
}