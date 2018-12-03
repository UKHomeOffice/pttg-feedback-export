package uk.gov.digital.ho.pttg.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import uk.gov.digital.ho.pttg.dto.FeedbackDto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedbackServiceTest {

    private FeedbackService feedbackService;

    @Mock
    private RestTemplate restTemplate;
    private static final String SOME_URL = "SOME_URL";
    private static final String SOME_AUTH = "SOME_AUTH";
    private static final String SOME_AUTH_BASE64 = Base64.getEncoder().encodeToString(SOME_AUTH.getBytes());

    @Captor
    private ArgumentCaptor<HttpEntity> httpEntityCaptor;

    @Test
    public void shouldAddBasicAuthHeader() {
        feedbackService = new FeedbackService(restTemplate, SOME_URL, SOME_AUTH);

        List<FeedbackDto> result = new ArrayList<>();
        ResponseEntity responseEntity = new ResponseEntity(result, HttpStatus.OK);
        when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(ParameterizedTypeReference.class))).thenReturn(responseEntity);

        feedbackService.getAllFeedback();

        verify(restTemplate).exchange(any(String.class), any(HttpMethod.class), httpEntityCaptor.capture(), any(ParameterizedTypeReference.class));
        assertThat(httpEntityCaptor.getValue().getHeaders().get(HttpHeaders.AUTHORIZATION)).contains("Basic " + SOME_AUTH_BASE64);
    }

}
