package uk.gov.digital.ho.pttg.api;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import uk.gov.digital.ho.pttg.jpa.FeedbackRepository;
import uk.gov.digital.ho.pttg.dto.FeedbackCsvView;
import uk.gov.digital.ho.pttg.jpa.Feedback;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.gov.digital.ho.pttg.jpa.FeedbackRepositoryTest.*;


@RunWith(MockitoJUnitRunner.class)
public class FeedbackResourceTest {


    private static final String COMBINED_INCOME_TRUE = "true";
    @Mock
    private FeedbackRepository mockRepo;

    @Mock
    private Model mockModel;

    @InjectMocks
    private FeedbackResource resource;

    @Before
    public void setUp() {

    }

    @Test
    public void testCollaboratorsGettingFeedback() throws IOException {


        when(mockRepo.findAllByOrderByTimestampDesc()).thenReturn(buildFeedbackList());

        resource.allFeedback(mockModel);

        verify(mockRepo).findAllByOrderByTimestampDesc();

        verify(mockModel).addAttribute("feedback", buildFeedbackViewList());
    }

    private List<Feedback> buildFeedbackList() {
        return ImmutableList.of(createFeedback(NOW, DETAIL), createFeedback(NOW_PLUS_60_MINS, DETAIL_NO_WHY_NOT_SECTION));
    }

    private Feedback createFeedback(LocalDateTime timestamp, String detail) {
        return Feedback.builder().detail(detail)
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
