package es.ulpgc.service;

import es.ulpgc.data.CSVDataSource;
import es.ulpgc.data.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource csvDataSource() {
        return new CSVDataSource("/app/data/index_content.csv", "/app/data/index_metadata.csv");
    }
}
