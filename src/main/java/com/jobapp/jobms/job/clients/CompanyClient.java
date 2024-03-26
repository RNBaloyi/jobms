package com.jobapp.jobms.job.clients;


import com.jobapp.jobms.job.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="COMPANY-SERVICE")
public interface CompanyClient {

  @GetMapping("/companies/{companyId}")
  Company getCompany(@PathVariable Long companyId);

}
//http://localhost:8081/companies/2



//Company
//companyId

// Company company = restTemplate.getForObject("http://COMPANY-SERVICE/companies/" + companyId, Company.class);