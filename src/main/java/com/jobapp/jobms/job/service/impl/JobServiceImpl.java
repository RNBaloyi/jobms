package com.jobapp.jobms.job.service.impl;


import com.jobapp.jobms.job.model.Job;
import com.jobapp.jobms.job.repo.JobRepository;
import com.jobapp.jobms.job.service.JobService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;


    @Override
    public  List<Job> findAll() {
        return jobRepository.findAll();
    }

//    @Override
//    public Optional<List<Job>> getJobsByCompanyId(Long companyId) {
//        return jobRepository.findAllByCompanyId(companyId);
//    }

    @Override
    public void createJob(Job job) {

        jobRepository.save(job);

    }

    @Override
    public Optional<Job> getJobById(Long id) {
        return jobRepository.findById(id);
    }

    @Override
    public boolean deleteJobById(Long id) {
        if (jobRepository.existsById(id)){
            jobRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job newJob) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isPresent()) {
            Job existingJob = optionalJob.get();
            existingJob.setTitle(newJob.getTitle());
            existingJob.setDescription(newJob.getDescription());
            existingJob.setMinSalary(newJob.getMinSalary());
            existingJob.setMaxSalary(newJob.getMaxSalary());
            existingJob.setLocation(newJob.getLocation());
            jobRepository.save(existingJob); // Save the updated job
            return true;
        } else {
            return false; // Job with the specified ID not found
        }
    }




}


















/*
* GET /jobs: get all jobs
GET /jobs/{id}: get a specific job by id
POST /jobs: Create a new job (request body should contain the job details)
DELETE /jobs/{id}: Delete a specific job by id
PUT /jobs/{id}: update a specific job by id(request body should contain the updated job details)
GET {base_url}/{id}/company:Get Jobs assoacited company with a specific job by id

Example API URls:
GET {base_url}/jobs
GET {base_url}/jobs/1
POST {base_url}/jobs
DELETE {base_url}/jobs/1
PUT {base_url}/jobs/1
GET {base_url}/jobs/1/company */
