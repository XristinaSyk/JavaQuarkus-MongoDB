package org.acme.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "books")
public interface MongoConfig {

    @WithName("database")
    String getDatabase();

    @WithName("collection")
    String getCollection();
}
