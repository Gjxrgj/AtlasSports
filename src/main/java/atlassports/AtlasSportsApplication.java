package atlassports;

import com.fasterxml.jackson.databind.Module;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class AtlasSportsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtlasSportsApplication.class, args);
    }

    @Bean(name = "org.openapitools.AtlasSportsApplication.jsonNullableModule")
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }

}
