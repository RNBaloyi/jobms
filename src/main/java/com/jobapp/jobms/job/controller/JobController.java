package com.jobapp.jobms.job.controller;




import com.jobapp.jobms.job.clients.CompanyClient;
import com.jobapp.jobms.job.clients.ReviewClient;
import com.jobapp.jobms.job.dto.JobDTO;
import com.jobapp.jobms.job.external.Company;
import com.jobapp.jobms.job.external.Review;
import com.jobapp.jobms.job.mapper.JobMapper;
import com.jobapp.jobms.job.model.Job;
import com.jobapp.jobms.job.service.impl.JobServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    RestTemplate restTemplate;

    private final JobServiceImpl jobService;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;

    public JobController(JobServiceImpl jobService) {
        this.jobService = jobService;
    }

    int Attempts=0;
    @GetMapping
//    @CircuitBreaker(name = "companyBreaker",
//            fallbackMethod ="companyBreakerFallBack" )

//    @Retry(name = "companyBreaker",
//            fallbackMethod ="companyBreakerFallBack" )
    @RateLimiter(name = "companyBreaker",fallbackMethod ="companyBreakerFallBack")
    public ResponseEntity<List<JobDTO>>  findAll(){
        List<Job> jobs = jobService.findAll();
        List<JobDTO>  jobDTOs = new ArrayList<>();
     Attempts++;
        System.out.println(Attempts);
        for (Job job : jobs) {
            Long companyId = job.getCompanyId();

           Company company=companyClient.getCompany(companyId);

            Review[] reviews = reviewClient.getReview(companyId).toArray(new Review[0]);
            // Convert array of reviews to a list
            List<Review> reviewList = Arrays.asList(reviews);

            JobDTO jobDTO= JobMapper.mapJobWithCompany(job, company,reviewList);

            jobDTOs.add(jobDTO);
        }
        return new ResponseEntity<>(jobDTOs,HttpStatus.OK);
    }




    public ResponseEntity<List<JobDTO>> companyBreakerFallBack(Throwable throwable) {
        List<JobDTO> fallbackList = new ArrayList<>();

        // Adding a sample JobDTO to the fallback list
        JobDTO fallbackJobDTO = new JobDTO();
        fallbackJobDTO.setId(0L);
        fallbackJobDTO.setTitle("Fallback Job Title");
        fallbackJobDTO.setDescription("Fallback Job Description");
        fallbackList.add(fallbackJobDTO);

        return new ResponseEntity<>(fallbackList, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<String>  addJob(@RequestBody Job job){
        jobService.createJob(job);
        String status = "job successfully added";
        return new ResponseEntity<>(status, HttpStatus.OK) ;
    }



    @GetMapping("/{id}")
    @CircuitBreaker(name = "companyBreaker")
    public ResponseEntity<JobDTO> getById(@PathVariable Long id){
        Optional<Job> optionalJob =jobService.getJobById(id);


        if (optionalJob.isPresent()){

            Job job = optionalJob.get();


            Company company = companyClient.getCompany(job.getCompanyId());
            Review[] review = reviewClient.getReview(job.getCompanyId()).toArray(new Review[0]);
            List<Review> reviewList = Arrays.asList(review);

            JobDTO jobDTO = JobMapper.mapJobWithCompany(job,company,reviewList);

            return new ResponseEntity<>(jobDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        boolean isDeleted = jobService.deleteJobById(id);
        if(isDeleted){
            return new ResponseEntity<>("job deleted successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("job not found",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable long id, @RequestBody Job job){
        boolean isUpdated = jobService.updateJob(id,job);
        if (isUpdated){
            return  new ResponseEntity<>("job updated successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("job not found",HttpStatus.NOT_FOUND);
    }



}

//    @GetMapping("/{companyId}")
//    public ResponseEntity<Optional<List<Job>>> findJobsByCompanyId(@PathVariable Long companyId){
//        Optional<List<Job>> optionalJobs = jobService.getJobsByCompanyId(companyId);
//
//        if (optionalJobs.isPresent()){
//            return new ResponseEntity<>(optionalJobs,HttpStatus.OK);
//        }else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }
