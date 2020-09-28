package com.kef.org.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kef.org.rest.model.GreivanceUpdateStatus;

@Repository
public interface GreivanceUpdateStatusRepository extends JpaRepository<GreivanceUpdateStatus, Integer> {

	public Optional<List<GreivanceUpdateStatus>> findByTrackingIdAndStatusOrderByUnderReviewDateDesc(Integer trackingId,String status);
}
