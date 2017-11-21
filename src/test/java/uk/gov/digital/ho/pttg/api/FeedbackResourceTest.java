package uk.gov.digital.ho.pttg.api;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import uk.gov.digital.ho.pttg.dto.FeedbackCsvView;
import uk.gov.digital.ho.pttg.dto.FeedbackDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class FeedbackResourceTest {

    private static final String SESSION_ID = "sessionID";
    private static final String DEPLOYMENT = "deployment";
    private static final String USER_ID = "me";
    private static final String UUID = "uuid";
    private static final String NAMESPACE = "env";
    private static final String DETAIL_NO_WHY_NOT_SECTION = "{\"nino\": \"JD123456C\", \"match\": \"yes\"}";
    private static final String DETAIL = "{\"nino\": \"JB557733D\", \"match\": \"yes\", \"whynot\": {\"combinedincome\": true, \"multiple_employers\": true, \"pay_frequency_change\": false}, \"caseref\": \"21111111\", \"matchOther\": \"test2\"}";
    private static LocalDateTime NOW = LocalDateTime.now();
    private static LocalDateTime NOW_MINUS_60_MINS = LocalDateTime.now().minusMinutes(60);
    private static LocalDateTime NOW_PLUS_60_MINS = LocalDateTime.now().plusMinutes(60);

    private static final String COMBINED_INCOME_TRUE = "true";
    @Mock
    private FeedbackService mockService;

    @Mock
    private Model mockModel;

    @InjectMocks
    private FeedbackResource resource;

    @Before
    public void setUp() {

    }

    @Test
    public void testCollaboratorsGettingFeedback() throws IOException {


        when(mockService.getAllFeedback()).thenReturn(buildFeedbackList());

        resource.allFeedback(mockModel);

        verify(mockService).getAllFeedback();

        verify(mockModel).addAttribute("feedback", buildFeedbackViewList());
    }

    private List<FeedbackDto> buildFeedbackList() {
        return ImmutableList.of(createFeedback(NOW, DETAIL), createFeedback(NOW_PLUS_60_MINS, DETAIL_NO_WHY_NOT_SECTION));
    }

    private FeedbackDto createFeedback(LocalDateTime timestamp, String detail) {
        return FeedbackDto.builder().detail(detail)
                .sessionId(SESSION_ID)
                .deployment(DEPLOYMENT)
                .timestamp(timestamp)
                .userId(USER_ID)
                .uuid(UUID)
                .namespace(NAMESPACE)
                .build();
    }


    private List<FeedbackCsvView> buildFeedbackViewList() {
        return ImmutableList.of(createFeedbackViewWithWhyNotSection(NOW), createFeedbackViewWithoutWhyNotSection(NOW_PLUS_60_MINS));
    }

    private FeedbackCsvView createFeedbackViewWithoutWhyNotSection(LocalDateTime timestamp) {
        return FeedbackCsvView.builder()
                .timestamp(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(timestamp))
                .userId(USER_ID)
                .nino("JD123456C")
                .match("yes")
                .build();
    }

    private FeedbackCsvView createFeedbackViewWithWhyNotSection(LocalDateTime timestamp) {
        return FeedbackCsvView.builder()
                .timestamp(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(timestamp))
                .userId(USER_ID)
                .match("yes")
                .nino("JB557733D")
                .caseref("21111111")
                .combinedIncome(COMBINED_INCOME_TRUE)
                .multiple_employers("true")
                .pay_frequency_change("false")
                .matchOther("test2")
                .build();
    }

}
