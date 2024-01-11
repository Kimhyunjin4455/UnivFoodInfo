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
import org.zerock.univFood.entity.UnivFood;
import org.zerock.univFood.entity.UnivFoodImage;
import org.zerock.univFood.repository.UnivFoodImageRepository;
import org.zerock.univFood.repository.UnivFoodRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class UnivFoodServiceImpl implements UnivFoodService{
    // 아래의 레포지토리를 주입받도록 구성
    private final UnivFoodRepository univFoodRepository;
    private final UnivFoodImageRepository imageRepository;
    @Transactional
    @Override
    public Long register(UnivFoodDTO univFoodDTO) {
        // dtoToEntity()에서 반환한 객체들을 이용해서 save 처리
        Map<String, Object> entityMap = dtoToEntity(univFoodDTO);
        UnivFood univFood = (UnivFood) entityMap.get("univFood");
        List<UnivFoodImage> univFoodImageList = (List<UnivFoodImage>) entityMap.get("imgList");

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
}
