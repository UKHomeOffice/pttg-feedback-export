package uk.gov.digital.ho.pttg.csv;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import uk.gov.digital.ho.pttg.dto.FeedbackCsvView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class CsvView extends AbstractCsvView {

    private static final String TITLE_NINO = "NINO";
    private static final String TITLE_USER_ID = "User ID";
    private static final String TITLE_TIMESTAMP = "Timestamp";
    private static final String TITLE_PAPER_IPS_MATCH = "Paper & IPS match";
    private static final String TITLE_WHYNOT= "Why Not?";
    private static final String TITLE_OTHER = "Other";
    private static final String MAPPING_USER_ID = "userId";
    private static final String MAPPING_TIMESTAMP = "timestamp";
    private static final String MAPPING_NINO = "nino";
    private static final String MAPPING_MATCH = "match";
    private static final String MAPPING_WHYNOT = "reasonForNotMatch";
    private static final String MAPPING_MATCH_OTHER = "matchOther";

    @Override
    protected void buildCsvDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setHeader("content-type", "text/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", "inline; filename=\"feedback-" + getFormattedTime() + ".csv\"");

        List<FeedbackCsvView> feedbackList = (List<FeedbackCsvView>) model.get("feedback");
        String[] header = {TITLE_USER_ID, TITLE_TIMESTAMP, TITLE_NINO, TITLE_PAPER_IPS_MATCH, TITLE_WHYNOT, TITLE_OTHER};
        String[] fieldMappings = {MAPPING_USER_ID, MAPPING_TIMESTAMP, MAPPING_NINO, MAPPING_MATCH, MAPPING_WHYNOT, MAPPING_MATCH_OTHER};
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.STANDARD_PREFERENCE);

        csvWriter.writeHeader(header);

        for (FeedbackCsvView feedback : feedbackList) {
            csvWriter.write(feedback, fieldMappings);
        }
        csvWriter.close();
    }

    private String getFormattedTime() {
        return DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());
    }
}