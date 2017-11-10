package uk.gov.digital.ho.pttg.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FeedbackWhyNot {

    private String combinedincome;
    private String multiple_employers;
    private String pay_frequency_change;

}
