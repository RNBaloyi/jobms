package com.jobapp.jobms.job.mapper;

import com.jobapp.jobms.job.dto.JobDTO;

import com.jobapp.jobms.job.external.Company;
import com.jobapp.jobms.job.external.Review;
import com.jobapp.jobms.job.model.Job;

import java.util.List;

public class JobMapper {


    public static JobDTO mapJobWithCompany(Job job, Company company, List<Review> review){
        JobDTO jobWithCompanyDTO =new JobDTO();


        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setCompany(company);
        jobWithCompanyDTO.setReview(review);

        return jobWithCompanyDTO;
    }
}

//
//                    jobWithCompanyDTO.setCompany(company);
//                    jobWithCompanyDTO.setJob(job);
//                    jobWithCompanyDTO.setId(job.getId());
//                    jobWithCompanyDTO.setDescription(job.getDescription());
//                    jobWithCompanyDTO.setTitle(job.getTitle());
//                    jobWithCompanyDTO.setLocation(job.getLocation());
//                    jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
//                    jobWithCompanyDTO.setMinSalary(job.getMinSalary());
