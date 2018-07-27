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
    private String match;
    private String whynot;
    private String matchOther;

}
