package uk.gov.digital.ho.pttg.api;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = "classpath:integrationtest.properties")
public class FeedbackResourceIntTest {


    private static final String FEEDBACK_ENDPOINT = "/feedback";

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule(options().port(8084));

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private TestRestTemplate restTemplate;


    @Before
    public void setup() throws IOException, JSONException {
        stubFor(get(urlPathMatching("/feedback"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value()).withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(buildFeedbackListResponse())));
    }

    @Test
    public void happyPath() throws Exception {

        ResponseEntity<String> response = restTemplate.exchange(FEEDBACK_ENDPOINT, HttpMethod.GET, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType().toString()).isEqualTo("text/csv;charset=UTF-8");
        assertThat(response.getBody()).contains("User ID,Timestamp,NINO,Paper & IPS match,Case ID,Combined Income,Multiple Employers,Pay Frequency Change,Other");
        assertThat(response.getBody()).contains("james.nail@digital.homeoffice.gov.uk,12-Sep-2017 14:45:48,JB557733D,yes,21111111,true,true,false,test2");

        verify(
            1,
            getRequestedFor(
                urlEqualTo("/feedback"))
        );
    }

    private String buildFeedbackListResponse() throws JSONException, IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream("/resourceIntTest.json"));
    }

}
