package com.jobapp.jobms.job.service;



import com.jobapp.jobms.job.model.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {
     List<Job> findAll();
    // Optional<List<Job>> getJobsByCompanyId(Long companyId);
     void createJob(Job job);
     Optional<Job> getJobById(Long id);
     boolean deleteJobById(Long id);
     boolean updateJob(Long id,Job job);
}
