package org.zerock.univFood.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.univFood.dto.PageRequestDTO;
import org.zerock.univFood.dto.PageResultDTO;
import org.zerock.univFood.dto.UnivFoodDTO;
import org.zerock.univFood.dto.UnivFoodImageDTO;
import org.zerock.univFood.entity.UnivFood;
import org.zerock.univFood.entity.UnivFoodImage;
import org.zerock.univFood.repository.ReviewRepository;
import org.zerock.univFood.repository.UnivFoodImageRepository;
import org.zerock.univFood.repository.UnivFoodRepository;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class UnivFoodServiceImpl implements UnivFoodService{
    // 아래의 레포지토리를 주입받도록 구성
    private final UnivFoodRepository univFoodRepository;
    private final UnivFoodImageRepository imageRepository;
    private final ReviewRepository reviewRepository;
    @Transactional
    @Override
    public Long register(UnivFoodDTO univFoodDTO) {
        // dtoToEntity()에서 반환한 객체들을 이용해서 save 처리
        Map<String, Object> entityMap = dtoToEntity(univFoodDTO); // String -> Object
        UnivFood univFood = (UnivFood) entityMap.get("univFood");
        List<UnivFoodImage> univFoodImageList = (List<UnivFoodImage>) entityMap.get("imgList");

        if(univFoodImageList.size() == 0){

        }

        univFoodRepository.save(univFood);

        univFoodImageList.forEach(univFoodImage -> {
            imageRepository.save(univFoodImage);
        });

        return univFood.getUno();
    }

    @Override
    public PageResultDTO<UnivFoodDTO, Object[]> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("uno").descending());

        Page<Object[]> result = univFoodRepository.getListPage(pageable);

        Function<Object[], UnivFoodDTO> fn = (arr-> entitiesToDTO(
                (UnivFood) arr[0],
                (List<UnivFoodImage>) (Arrays.asList((UnivFoodImage)arr[1])),
                (Double) arr[2],
                (Long) arr[3]
        ));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public UnivFoodDTO entitiesToDTO(UnivFood univFood, List<UnivFoodImage> univFoodImages, Double avg, Long reviewCnt) {
        return UnivFoodService.super.entitiesToDTO(univFood, univFoodImages, avg, reviewCnt);
    }

    @Override
    public UnivFoodDTO getUnivFood(Long uno) {
        // uno를 파라미터로 받아서 엔티티의 값을 얻은 후 DTO변환함수(DTO 반환)를 반환

        List<Object[]> result = univFoodRepository.getUnivFoodWithAll(uno);

            UnivFood univFood = (UnivFood) result.get(0)[0];            // UnivFood 엔티티는 가장 앞에 존재하는 모든 row가 동일 값


            // uno가 2인 엔티티의 이미지가 2개가 있으면 두 개의 uno는 2로 동일함
            List<UnivFoodImage> univFoodImageList = new ArrayList<>();  // 식당의 이미지 개수만큼 UnivFoodImage객체 필요함

            result.forEach(arr -> {
                UnivFoodImage univFoodImage = (UnivFoodImage) arr[1];
                univFoodImageList.add(univFoodImage);
            });

            Double avg = (Double) result.get(0)[2]; // 평점 각 uno에 대해 모두 같은 값
            Long reviewCnt = (Long) result.get(0)[3];

            return entitiesToDTO(univFood, univFoodImageList, avg, reviewCnt);


    }

    @Transactional
    @Override
    public void remove(Long uno){
        UnivFood univFood = UnivFood.builder().uno(uno).build();

        reviewRepository.deleteByUnivFood(univFood);
        imageRepository.deleteByUnivFoodImage(univFood);
        univFoodRepository.deleteById(uno);

    }

    @Transactional
    @Override
    public Long modify(UnivFoodDTO univFoodDTO){

        //UnivFood univFood = univFoodRepository.getById(univFoodDTO.getUno());

        Map<String, Object> entityMap = dtoToEntity(univFoodDTO); // String -> Object
        UnivFood univFood = (UnivFood) entityMap.get("univFood");
        List<UnivFoodImage> univFoodImageList = (List<UnivFoodImage>) entityMap.get("imgList");

        if(univFoodImageList == null){
            imageRepository.deleteByUnivFoodImage(univFood);

            UnivFoodImage univFoodImage = UnivFoodImage.builder()
                    .path("No path")
                    .uuid(UUID.randomUUID().toString())
                    .univFood(univFood)
                    .imgName("NO IMAGE.jpg")
                    .build();

            UnivFoodImageDTO univFoodImageDTO = UnivFoodImageDTO.builder()
                    .imgName(univFoodImage.getImgName())
                    .path(univFoodImage.getPath())
                    .uuid(univFoodImage.getUuid()).build();

            List<UnivFoodImageDTO> univFoodImageDTOList = new ArrayList<>();
            univFoodImageDTOList.add(univFoodImageDTO);
            univFoodDTO.setImageDTOList(univFoodImageDTOList);


            imageRepository.save(univFoodImage);

            univFood.changeRestaurantName(univFoodDTO.getRestaurantName());
            univFood.changeSignatureMenu(univFoodDTO.getSignatureMenu());
            univFood.changeContact(univFoodDTO.getContact());
            univFood.changeAddress(univFoodDTO.getAddress());

            return univFood.getUno();
        }

        univFood.changeRestaurantName(univFoodDTO.getRestaurantName());
        univFood.changeSignatureMenu(univFoodDTO.getSignatureMenu());
        univFood.changeContact(univFoodDTO.getContact());
        univFood.changeAddress(univFoodDTO.getAddress());


        imageRepository.deleteByUnivFoodImage(univFood); // 기존이랑 다르면 재작성하기 기능 추가
        univFoodImageList.forEach(univFoodImage -> {
            imageRepository.save(univFoodImage);
        });



        univFoodRepository.save(univFood);

        return univFood.getUno();
    }

}
