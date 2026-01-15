package com.itwillbs.LaClave.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.itwillbs.LaClave.Cart.ItemImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryResponseDto {

    private Long productIdx;

    private String productName;

    private int productPrice;

    private List<Long> colorCommonIdx;
    private List<String> colors;

    private List<String> sizes;
    // private List<Long> sizeCommonIdx;

    private double averageRating;

    private String mainImageUrl; // 목록

    private List<String> detailImages; // 상세

    // 카테고리 항목 가져오기
    public CategoryResponseDto(Item item) {
        this.productIdx = item.getProductIdx();
        this.productName = item.getProductName();
        this.productPrice = item.getProductPrice();

        System.out.println("=== DTO 생성 시작: " + item.getProductName() + " (ID: " + item.getProductIdx() + ") ===");
        System.out.println("Images 개수: " + (item.getImages() != null ? item.getImages().size() : "null"));
        System.out.println("Options 개수: " + (item.getOptions() != null ? item.getOptions().size() : "null"));

        // 이미지가 있을 경우 처리
        if (item.getImages() != null && !item.getImages().isEmpty()) {
            List<String> allUrls = new ArrayList<>();
            for (ItemImage img : item.getImages()) {
                String url = img.getUrl();
                System.out.println("이미지 URL: " + url);
                if (url != null && !url.isEmpty()) {
                    allUrls.add(url);
                }
            }
            if (!allUrls.isEmpty()) {
                this.mainImageUrl = allUrls.get(0);
                this.detailImages = allUrls;
                System.out.println("메인 이미지 설정됨: " + this.mainImageUrl);
            }
        } else {
            System.out.println("이미지 없음!");
        }

        // 색상 처리 - hex 값으로 변환
        this.colors = extractColors(item);
        this.sizes = extractSizes(item);

        System.out.println("색상 개수: " + (this.colors != null ? this.colors.size() : "null"));
        System.out.println("사이즈 개수: " + (this.sizes != null ? this.sizes.size() : "null"));
        System.out.println("=== DTO 생성 완료 ===\n");
    }

    // 색상 코드를 hex 값으로 변환
    private List<String> extractColors(Item item) {
        if (item.getOptions() == null || item.getOptions().isEmpty()) {
            return new ArrayList<>();
        }

        return item.getOptions().stream()
                .map(opt -> {
                    if (opt.getColorCategory() != null) {
                        // code_desc에 hex 값이 있으면 사용, 없으면 code 사용
                        String colorHex = opt.getColorCategory().getCodeDesc();
                        if (colorHex != null && !colorHex.isEmpty()) {
                            return colorHex;
                        }
                        return opt.getColorCategory().getCode();
                    }
                    return null;
                })
                .filter(java.util.Objects::nonNull)
                .distinct()
                .toList();
    }

    // 사이즈 추출
    private List<String> extractSizes(Item item) {
        if (item.getOptions() == null || item.getOptions().isEmpty()) {
            return new ArrayList<>();
        }

        return item.getOptions().stream()
                .map(opt -> opt.getSizeCategory() != null ? opt.getSizeCategory().getCode() : null)
                .filter(java.util.Objects::nonNull)
                .distinct()
                .toList();
    }

    // 사용하지 않는 메서드들 (필요시 삭제 가능)
    private List<String> extractNames(Item item, Function<ProductOption, String> mapper) {
        return item.getOptions().stream()
                .map(mapper)
                .filter(java.util.Objects::nonNull)
                .distinct()
                .toList();
    }

    private List<Long> extractIdxs(Item item, Function<ProductOption, Long> mapper) {
        return item.getOptions().stream()
                .map(mapper)
                .distinct()
                .toList();
    }
}
