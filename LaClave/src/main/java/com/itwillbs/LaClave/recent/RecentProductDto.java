package com.itwillbs.LaClave.recent;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class RecentProductDto {
    private Integer productIdx;
    private LocalDateTime viewedAt;
}