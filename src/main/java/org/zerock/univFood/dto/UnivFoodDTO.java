package org.zerock.univFood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// UnivFoodDTO 클래스의 내부에는 업로드된 파일(이미지)들의 정보도 포함되어야 함 -> UnivFoodImageDTO 클래스도 추가
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnivFoodDTO {
    private Long uno;
    private String restaurantName;
    private String signatureMenu;
    private String contact;
    private String address;

    @Builder.Default
    private List<UnivFoodImageDTO> imageDTOList = new ArrayList<>();
    // 내부적으로 리스트를 이용해 이미지 수집

}
