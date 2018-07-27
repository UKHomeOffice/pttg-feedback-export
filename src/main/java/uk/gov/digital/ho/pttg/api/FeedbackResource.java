package uk.gov.digital.ho.pttg.api;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.gov.digital.ho.pttg.dto.FeedbackCsvView;
import uk.gov.digital.ho.pttg.dto.FeedbackDetail;
import uk.gov.digital.ho.pttg.dto.FeedbackDto;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@Component
public class FeedbackResource {


    static final String CSV_DATE_FORMAT = "d MMM uuuu h:mm:ss a";
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
        FeedbackDetail detail = mapper.fromJson(f.getDetail().replace("\"whynot\": {},", ""), FeedbackDetail.class);
        String whynot = detail.getWhynot() != null ? detail.getWhynot().replace("-", " ") : "";
        final String formattedTimestamp = f.getTimestamp() !=null ? DateTimeFormatter.ofPattern(CSV_DATE_FORMAT).withLocale(Locale.ENGLISH).format(f.getTimestamp()) : "";
        return FeedbackCsvView.builder()
                .timestamp(formattedTimestamp)
                .nino(detail.getNino())
                .userId(f.getUserId())
                .match(detail.getMatch())
                .whynot(whynot)
                .matchOther(detail.getMatchOther())
                .build();
    }
}