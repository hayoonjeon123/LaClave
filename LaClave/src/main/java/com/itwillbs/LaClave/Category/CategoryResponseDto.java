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
    private List<String> colors;
    
    private List<String> sizes; 
//    private List<Long> sizeCommonIdx; 
    
    private double averageRating;
    
    private String Image;
    

    // 카테고리 항목 가져오기
    public CategoryResponseDto(Item item) {
        this.productName = item.getProductName();
        this.productPrice = item.getProductPrice(); 
        
//        this.averageRating = (avgRating != null) ? avgRating : 0.0;

     // 추가: 이미지 리스트에서 '대표사진' 상태인 이미지를 찾아 경로를 저장합니다.
//        if (item.getImages() != null) {
//            this.Image = item.getImages().stream()
//                    .filter(img -> img.getStatusCategory() != null && 
//                                   "대표사진".equals(img.getStatusCategory().getCodeDesc())) // 공통코드 설명 확인
//                    .map(ProductImage::getStoredPath)
//                    .findFirst()
//                    .orElse(null); // 만약 대표사진이 없으면 null
//        }
        
        this.colors = extractNames(item, opt -> opt.getColorCategory().getCode());
        this.sizes = extractNames(item, opt -> opt.getSizeCategory().getCode());
         
    }
    
    private List<String> extractNames(Item item, Function<ProductOption, String> mapper) {
        return item.getOptions().stream()
                .map(mapper)
                .filter(java.util.Objects::nonNull)
                .distinct()
                .toList();
    }
    
    // 색상, 사이즈 리스트 만들기
    private List<Long> extractIdxs(Item item, Function<ProductOption, Long> mapper) {
        return item.getOptions().stream()
                .map(mapper)
                .distinct()
                .toList();
    }
}

