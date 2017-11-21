package uk.gov.digital.ho.pttg.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import uk.gov.digital.ho.pttg.SpringConfiguration;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FeedbackDtoSerialisationTest {

    private static final LocalDateTime TIMESTAMP = LocalDateTime.of(2017, 9, 11, 14, 45, 48);
    public static final String USER_ID = "some user id";
    public static final String DETAIL = "{\"nino\": \"JD123456C\",\"match\": \"yes\"}";
    public static final String SESSION_ID = "some session id";
    public static final String UUID = "some uuid";


    private ObjectMapper mapper = SpringConfiguration.initialiseObjectMapper(new ObjectMapper());


    @Test
    public void shouldSerialiseToExpectedJson() throws Exception {
        final String feedbackAsString = mapper.writeValueAsString(buildFeedbackDto());
        assertThat(feedbackAsString).isEqualTo(getJsonRepresentation());
    }

    @Test
    public void shouldDeSerialiseFromJson() throws Exception {
        final FeedbackDto feedbackDto = mapper.readValue(getJsonRepresentation(), FeedbackDto.class);

        assertThat(feedbackDto).isEqualTo(buildFeedbackDto());
    }

    private String getJsonRepresentation() throws JSONException, IOException {
        return IOUtils.toString(this.getClass().getResourceAsStream("/feedbackDto.json"));

    }


    private FeedbackDto buildFeedbackDto() {
        return new FeedbackDto(UUID, TIMESTAMP, SESSION_ID, null, null, USER_ID, DETAIL);
    }

}