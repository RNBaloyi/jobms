package com.jobapp.jobms.job.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Long Id;
    private String title;
    private String description;
    private double rating;
    Long companyId;
}
