package com.itwillbs.LaClave.point;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {
    private Long pointIdx;
    private Integer pointAmount;
    private Long orderIdx;
    private String description;
    private LocalDate createdAt;
}