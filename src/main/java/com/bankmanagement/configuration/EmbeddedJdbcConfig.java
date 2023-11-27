package com.bankmanagement.configuration;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.logging.Logger;

public class EmbeddedJdbcConfig {
//    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(EmbeddedJdbcConfig.class);
//    @Bean
//    public DataSource dataSource(){
//        try{
//            var dbBuilder = new EmbeddedDatabaseBuilder();
//            return dbBuilder.setType(EmbeddedDatabaseType.H2)
//                    .addScripts("classpath:h2/schema.sql","classpath:h2/test-data-sql")
//                    .build();
//        }catch (Exception e){
//           // LOGGER.log("Embedded datasource bean cannot be created!",e);
//            return null;
//        }
//    }
}
