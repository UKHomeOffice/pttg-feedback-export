package uk.gov.digital.ho.pttg.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.gov.digital.ho.pttg.dto.FeedbackDto;

import java.util.List;

@Service
public class FeedbackService {

    private static final ParameterizedTypeReference<List<FeedbackDto>> feedbackResourceTypeRef = new ParameterizedTypeReference<List<FeedbackDto>>() {
    };

    private final RestTemplate restTemplate;
    private final String url;

    public FeedbackService(RestTemplate restTemplate, @Value("${feedback.endpoint}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public List<FeedbackDto> getAllFeedback(){
        final HttpEntity<Object> requestEntity = new HttpEntity<>(null, generateHeaders());
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, feedbackResourceTypeRef).getBody();
    }

    private static HttpHeaders generateHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
