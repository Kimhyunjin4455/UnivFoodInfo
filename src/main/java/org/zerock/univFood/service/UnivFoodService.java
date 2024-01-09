package org.zerock.univFood.service;

import org.zerock.univFood.dto.UnivFoodDTO;
import org.zerock.univFood.dto.UnivFoodImageDTO;
import org.zerock.univFood.entity.UnivFood;
import org.zerock.univFood.entity.UnivFoodImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface UnivFoodService {
    Long register(UnivFoodDTO univFoodDTO);

    // UnivFood를 JPA로 처리하기 위해 UnivFoodDTO를 UnivFood 객체로 변환해주어야 함
    // -> dtoToEntity() 추가 (UnivFoodImage 객체들도 같이 처리하기 위해 Map 타입을 이용해 반환)
    default Map<String, Object> dtoToEntity(UnivFoodDTO univFoodDTO){ // Map 타입으로 변환
        // Map 타입으로 UnivFood 객체와 UnivFoodImage 객체의 리스트를 처리
        Map<String, Object> entityMap = new HashMap<>();

        UnivFood univFood = UnivFood.builder()                        // DTO를 정보를 바탕으로 univFood객체 생성
                .uno(univFoodDTO.getUno())
                .restaurantName(univFoodDTO.getRestaurantName())
                .signatureMenu(univFoodDTO.getSignatureMenu())
                .contact(univFoodDTO.getContact())
                .address(univFoodDTO.getAddress())
                .build();

        entityMap.put("univFood", univFood);

        List<UnivFoodImageDTO> imageDTOList = univFoodDTO.getImageDTOList();
        // UnivFoodImageDTO 처리
        if(imageDTOList != null && imageDTOList.size() > 0){            // 이미지DTO가 유효하게 존재한다면
            List<UnivFoodImage> univFoodImageList = imageDTOList.stream().map(univFoodImageDTO -> { // univFoodImageDTO는 imageDTOList의 것들을 다루기 위한 변수             // 각 이미지별로 분리해서
                UnivFoodImage univFoodImage = UnivFoodImage.builder()   // 이미지DTO와 univFood객체를 이용해서
                        .path(univFoodImageDTO.getPath())               // 음식 이미지 객체 반환
                        .imgName(univFoodImageDTO.getImgName())
                        .uuid(univFoodImageDTO.getUuid())
                        .univFood(univFood)
                        .build();
                return univFoodImage;
            }).collect(Collectors.toList());                            // 닫는 스트림

            entityMap.put("imgList", univFoodImageList);
        }

        return entityMap;
    }
}
