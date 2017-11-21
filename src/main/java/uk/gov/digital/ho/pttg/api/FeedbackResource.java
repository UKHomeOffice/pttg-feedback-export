package uk.gov.digital.ho.pttg.api;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.gov.digital.ho.pttg.dto.FeedbackCsvView;
import uk.gov.digital.ho.pttg.dto.FeedbackDetail;
import uk.gov.digital.ho.pttg.dto.FeedbackDto;
import uk.gov.digital.ho.pttg.dto.FeedbackWhyNot;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Component
public class FeedbackResource {


    private final FeedbackService service;

    public FeedbackResource(FeedbackService service) {
        this.service = service;
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String allFeedback(Model model) throws IOException {
        List<FeedbackDto> feedbackJpaList = service.getAllFeedback();
        final List<FeedbackCsvView> csvViews = feedbackJpaList.stream().map(this::buildCsvView).collect(Collectors.toList());

        model.addAttribute("feedback", csvViews);
        return "not_used";
    }

    private FeedbackCsvView buildCsvView(FeedbackDto f) {
        Gson mapper = new Gson();
        FeedbackDetail detail = mapper.fromJson(f.getDetail(), FeedbackDetail.class);
        FeedbackWhyNot whynot = detail.getWhynot();
        final String matchOther = detail.getMatchOther() == null ? detail.getMatchComment() : detail.getMatchOther();
        return FeedbackCsvView.builder()
                .timestamp(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(f.getTimestamp()))
                .nino(detail.getNino())
                .userId(f.getUserId())
                .match(detail.getMatch())
                .combinedIncome(whynot != null ? whynot.getCombinedincome() : null)
                .multiple_employers(whynot != null  ? whynot.getMultiple_employers() : null)
                .pay_frequency_change(whynot != null ? whynot.getPay_frequency_change() : null)
                .caseref(detail.getCaseref())
                .matchOther(matchOther)
                .build();
    }
}