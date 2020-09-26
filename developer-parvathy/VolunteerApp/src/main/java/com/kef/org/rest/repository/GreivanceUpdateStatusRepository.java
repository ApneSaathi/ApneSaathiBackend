package com.kef.org.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kef.org.rest.model.GreivanceUpdateStatus;

@Repository
public interface GreivanceUpdateStatusRepository extends JpaRepository<GreivanceUpdateStatus, Integer> {

}
