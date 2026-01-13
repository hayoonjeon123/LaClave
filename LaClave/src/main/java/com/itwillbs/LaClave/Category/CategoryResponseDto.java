package com.itwillbs.LaClave.Category;


import java.util.List;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryResponseDto {
	private String productName;
	
    private int productPrice;
    
    private List<Long> colorCommonIdx;
    
    private List<Long> sizeCommonIdx; 
    
    private double averageRating;
    

    // 카테고리 항목 가져오기
    public CategoryResponseDto(Item item) {
        this.productName = item.getProductName();
        this.productPrice = item.getProductPrice(); 
        
//        this.averageRating = (avgRating != null) ? avgRating : 0.0;

//        this.colorCommonIdx = extractIdxs(item, opt -> opt.getColorCommonIdx());
//        this.sizeCommonIdx = extractIdxs(item, opt -> opt.getSizeCommonIdx());
    }
    
    // 색상, 사이즈 리스트 만들기
//    private List<Long> extractIdxs(Item item, Function<ProductOption, Long> mapper) {
//        return item.getOptions().stream()
//                .map(mapper)
//                .distinct()
//                .toList();
//    }
}

