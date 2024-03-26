package com.jobapp.jobms.job.clients;


import com.jobapp.jobms.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEW-SERVICE")
public interface ReviewClient {

    @GetMapping("/reviews")
    List<Review> getReview(@RequestParam("companyId") Long companyId);

}

// Review[] reviews = restTemplate.getForObject("http://REVIEW-SERVICE/reviews?companyId=" + companyId, Review[].class);
