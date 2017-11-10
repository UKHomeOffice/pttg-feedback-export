package uk.gov.digital.ho.pttg.api;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.gov.digital.ho.pttg.jpa.FeedbackRepository;
import uk.gov.digital.ho.pttg.dto.FeedbackCsvView;
import uk.gov.digital.ho.pttg.dto.FeedbackDetail;
import uk.gov.digital.ho.pttg.dto.FeedbackWhyNot;
import uk.gov.digital.ho.pttg.jpa.Feedback;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Controller
@Component
public class FeedbackResource {

    @Autowired
    FeedbackRepository repo;


    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String allFeedback(Model model) throws IOException {
        List<Feedback> feedbackJpaList = repo.findAllByOrderByTimestampDesc();
        final List<FeedbackCsvView> csvViews = new ArrayList<>();
        for (Feedback f : feedbackJpaList) {
            FeedbackCsvView feedbackCsvView = buildCsvView(f);
            csvViews.add(feedbackCsvView);
        }

        model.addAttribute("feedback", csvViews);
        return "not_used";
    }

    private FeedbackCsvView buildCsvView(Feedback f) {
        Gson mapper = new Gson();
        FeedbackDetail detail = mapper.fromJson(f.getDetail(), FeedbackDetail.class);
        FeedbackWhyNot whynot = detail.getWhynot();
        return FeedbackCsvView.builder()
                .timestamp(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(f.getTimestamp()))
                .nino(detail.getNino())
                .userId(f.getUserId())
                .match(detail.getMatch())
                .combinedIncome(whynot != null ? whynot.getCombinedincome() : null)
                .multiple_employers(whynot != null  ? whynot.getMultiple_employers() : null)
                .pay_frequency_change(whynot != null ? whynot.getPay_frequency_change() : null)
                .caseref(detail.getCaseref())
                .matchOther(detail.getMatchOther())
                .build();
    }
}