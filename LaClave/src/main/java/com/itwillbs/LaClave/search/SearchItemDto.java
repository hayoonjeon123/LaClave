package com.itwillbs.LaClave.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchItemDto {
    private Long productIdx;

    private String productName;

    private int productPrice;

    private String mainImageUrl;
}
