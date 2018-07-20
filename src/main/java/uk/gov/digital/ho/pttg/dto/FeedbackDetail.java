package uk.gov.digital.ho.pttg.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class FeedbackDetail {

    private String nino;
    private String match;
    private String whynot;
    private String matchOther;

}
