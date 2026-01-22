package com.itwillbs.LaClave.commoncode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonCodeService {

    private final CommonCodeRepository commonCodeRepository;

    public String getLabel(Long commonIdx) {
        if (commonIdx == null) return "-";

        return commonCodeRepository.findById(commonIdx)
                .filter(code -> "Y".equals(code.getUsingStatus()))
                .map(CommonCode::getCodeLabel)
                .orElse("-");
    }


    
    public String getLabelByCommonIdx(Long deliveryStatusCommonIdx) {

        if (deliveryStatusCommonIdx == null) {
            return "상태 없음";
        }

        return commonCodeRepository
                .findById(deliveryStatusCommonIdx)
                .map(CommonCode::getCodeLabel)
                .orElse("상태 없음");
    }
}