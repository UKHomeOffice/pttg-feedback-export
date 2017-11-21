package uk.gov.digital.ho.pttg.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@ToString
public class FeedbackCsvView {

    private String timestamp;
    private String userId;
    private String nino;
    private String combinedIncome;
    private String multiple_employers;
    private String pay_frequency_change;
    private String caseref;
    private String match;
    private String matchOther;

}
