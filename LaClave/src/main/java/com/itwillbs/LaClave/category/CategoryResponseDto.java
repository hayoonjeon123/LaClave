package com.itwillbs.LaClave.category;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.itwillbs.LaClave.image.Image;

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
        this.discount = 0; // 또는 테이블에 맞게 수정
        this.productDetailDesc = "";
        this.productShortDesc = "";
        this.productMaterial = "";

        // 리뷰 관련 (
        this.averageRating = 0.0;
        this.reviewCount = 0;

        if (item.getImages() != null && !item.getImages().isEmpty()) {
            List<String> allUrls = new ArrayList<>();
            String foundMainUrl = null;

            for (Image img : item.getImages()) {
                String url = img.getUrl();
                String targetType = img.getTargetType();
                String targetCode = img.getTargetCode();


                if ("REVIEW".equals(targetType) || "img_04".equals(targetCode)) {
                    continue;
                }

                if (url != null && !url.isEmpty()) {
                    if (!url.startsWith("http") && !url.startsWith("/images/")) {
                        url = "/images/" + url;
                    }

                    if ("img_01".equals(targetCode)) {
                        foundMainUrl = url;
                    }
                }
            }

            this.mainImageUrl = (foundMainUrl != null) ? foundMainUrl : (!allUrls.isEmpty() ? allUrls.get(0) : null);
            this.detailImages = allUrls;
        } 
        // 색상 처리
        this.colors = extractAttribute(item, opt -> opt.getColorCategory() != null
                ? (opt.getColorCategory().getCodeDesc() != null && opt.getColorCategory().getCodeDesc().startsWith("#")
                        ? opt.getColorCategory().getCodeDesc()
                        : opt.getColorCategory().getCode())
                : null);

        this.colorCommonIdx = extractAttribute(item,
                opt -> opt.getColorCategory() != null ? opt.getColorCategory().getCommonIdx() : null);

        // 사이즈 처리
        this.sizes = extractAttribute(item,
                opt -> opt.getSizeCategory() != null ? opt.getSizeCategory().getCode() : null);

        this.sizeCommonIdx = extractAttribute(item,
                opt -> opt.getSizeCategory() != null ? opt.getSizeCategory().getCommonIdx() : null);
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
