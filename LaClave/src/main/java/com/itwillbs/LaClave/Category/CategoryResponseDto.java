package com.itwillbs.LaClave.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.itwillbs.LaClave.Cart.ItemImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@Log4j2
public class CategoryResponseDto {

    private Long productIdx;

    private String productName;

    private int productPrice;

    private List<Long> colorCommonIdx;
    private List<String> colors;

    private List<String> sizes;
    private List<Long> sizeCommonIdx;

    private int discount;

    private double averageRating;

    private String mainImageUrl; 

    private List<String> detailImages; 

    private String productDetailDesc; 

    private String productShortDesc; 

    private String productMaterial; 

    private int reviewCount; 

    private int wishlistCount; 

    // 카테고리 항목 가져오기
    public CategoryResponseDto(Item item) {
        this.productIdx = item.getProductIdx();
        this.productName = item.getProductName();
        this.productPrice = item.getProductPrice();
        this.discount = item.getProductDiscountRate();
        this.productDetailDesc = item.getProductDetailDesc();
        this.productShortDesc = item.getProductShortDesc();
        this.productMaterial = item.getProductMaterial();

        // 리뷰 관련 (
        this.averageRating = 0.0;
        this.reviewCount = 0;

        // 이미지가 있을 경우 처리
        if (item.getImages() != null && !item.getImages().isEmpty()) {
            List<String> allUrls = new ArrayList<>();
            for (ItemImage img : item.getImages()) {
                String url = img.getUrl();
                log.info("이미지 URL:" , url);
                if (url != null && !url.isEmpty()) {
                    allUrls.add(url);
                }
            }
            if (!allUrls.isEmpty()) {
                this.mainImageUrl = allUrls.get(0);
                this.detailImages = allUrls;
                log.info("메인 이미지 설정됨::" , this.mainImageUrl);
            }
        } else {
            log.info("이미지 없음");
        }

     // 색상 처리
        this.colors = extractAttribute(item, opt -> 
            opt.getColorCategory() != null ? 
            (opt.getColorCategory().getCodeDesc() != null && opt.getColorCategory().getCodeDesc().startsWith("#") ? 
             opt.getColorCategory().getCodeDesc() : opt.getColorCategory().getCode()) : null);
        
        this.colorCommonIdx = extractAttribute(item, opt -> 
            opt.getColorCategory() != null ? opt.getColorCategory().getCommonIdx() : null);

        // 사이즈 처리
        this.sizes = extractAttribute(item, opt -> 
            opt.getSizeCategory() != null ? opt.getSizeCategory().getCode() : null);
        
        this.sizeCommonIdx = extractAttribute(item, opt -> 
            opt.getSizeCategory() != null ? opt.getSizeCategory().getCommonIdx() : null);
    }

    // extractSizes, extractNames, extractIdxs 합침
    private <T> List<T> extractAttribute(Item item, Function<ProductOption, T> mapper) {
        if (item.getOptions() == null || item.getOptions().isEmpty()) {
            return new ArrayList<>();
        }
        return item.getOptions().stream()
                .map(mapper)
                .filter(java.util.Objects::nonNull)
                .distinct()
                .toList();
    }
}
