package uk.gov.digital.ho.pttg;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import uk.gov.digital.ho.pttg.csv.CsvViewResolver;
import uk.gov.digital.ho.pttg.xlsx.ExcelViewResolver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SpringConfiguration extends WebMvcConfigurerAdapter {


    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);

        // Define all possible view resolvers
        List<ViewResolver> resolvers = new ArrayList<>();

        resolvers.add(csvViewResolver());
        resolvers.add(excelViewResolver());

        resolver.setViewResolvers(resolvers);
        return resolver;
    }

    public SpringConfiguration(ObjectMapper objectMapper) {
        initialiseObjectMapper(objectMapper);
    }

    public static ObjectMapper initialiseObjectMapper(final ObjectMapper m) {
        m.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        m.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        m.enable(SerializationFeature.INDENT_OUTPUT);
        m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return m;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    /*
     * Configure View resolver to provide Csv output using Super Csv library to
     * generate Csv output for an object content
*/
    @Bean
    public ViewResolver csvViewResolver() {
        return new CsvViewResolver();
    }


    @Bean
    public ViewResolver excelViewResolver() {
        return new ExcelViewResolver();
    }

}

