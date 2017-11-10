package uk.gov.digital.ho.pttg.jpa;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Access(AccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Feedback {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(name = "uuid", nullable = false)
    @Getter
    private String uuid;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
    @Column(name = "timestamp", nullable = false)
    @Getter
    private LocalDateTime timestamp;

    @Column(name = "session_id", nullable = false)
    @Getter
    private String sessionId;

    @Column
    @Getter
    private String namespace;

    @Column(name = "deployment", nullable = false)
    @Getter
    private String deployment;

    @Column(name = "user_id", nullable = false)
    @Getter
    private String userId;

    @Column(name = "detail", nullable = false)
    @Getter
    private String detail;


}
