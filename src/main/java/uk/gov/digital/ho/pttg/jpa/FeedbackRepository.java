package uk.gov.digital.ho.pttg.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

    List<Feedback> findAllByOrderByTimestampDesc();

}


