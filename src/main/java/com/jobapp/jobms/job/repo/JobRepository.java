package com.jobapp.jobms.job.repo;

import com.jobapp.jobms.job.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job,Long> {

    Optional<List<Job>> findAllByCompanyId(Long companyId);
}
