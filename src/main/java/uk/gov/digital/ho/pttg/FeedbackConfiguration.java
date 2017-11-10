package uk.gov.digital.ho.pttg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import uk.gov.digital.ho.pttg.csv.CsvViewResolver;
import uk.gov.digital.ho.pttg.xlsx.ExcelViewResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FeedbackConfiguration extends WebMvcConfigurerAdapter {


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

