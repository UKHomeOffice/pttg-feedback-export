package uk.gov.digital.ho.pttg.jpa;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@Profile("logtoconsole")
public class FeedbackRepositoryTest {

    public static final String SESSION_ID = "sessionID";
    public static final String DEPLOYMENT = "deployment";
    public static final String USER_ID = "me";
    public static final String UUID = "uuid";
    public static final String NAMESPACE = "env";
    public static final String DETAIL_NO_WHY_NOT_SECTION = "{\"nino\": \"JD123456C\", \"match\": \"yes\"}";
    public static final String DETAIL = "{\"nino\": \"JB557733D\", \"match\": \"yes\", \"whynot\": {\"combinedincome\": true, \"multiple_employers\": true, \"pay_frequency_change\": false}, \"caseref\": \"21111111\", \"matchOther\": \"test2\"}";
    public static LocalDateTime NOW = LocalDateTime.now();
    private static LocalDateTime NOW_MINUS_60_MINS = LocalDateTime.now().minusMinutes(60);
    public static LocalDateTime NOW_PLUS_60_MINS = LocalDateTime.now().plusMinutes(60);



    @Autowired
    private FeedbackRepository repository;

    @Before
    public void setup() {
        repository.save(createFeedback(NOW));
        repository.save(createFeedback(NOW_MINUS_60_MINS));
        repository.save(createFeedback(NOW_PLUS_60_MINS));
    }



    @Test
    public void shouldRetrieveAllFeedback() {

        final Iterable<Feedback> all = repository.findAll();
        assertThat(all).size().isEqualTo(3);
    }

    @Test
    public void shouldRetrieveAllFeedbackOrderedByTimestampDesc() {

        final Iterable<Feedback> all = repository.findAllByOrderByTimestampDesc();
        assertThat(all).size().isEqualTo(3);
        assertThat(all).extracting("timestamp").containsExactly(NOW_PLUS_60_MINS, NOW, NOW_MINUS_60_MINS);


    }

    private Feedback createFeedback(LocalDateTime timestamp) {
        return Feedback.builder().detail(DETAIL)
                .sessionId(SESSION_ID)
                .deployment(DEPLOYMENT)
                .timestamp(timestamp)
                .userId(USER_ID)
                .uuid(UUID)
                .namespace(NAMESPACE)
                .build();
    }

}
