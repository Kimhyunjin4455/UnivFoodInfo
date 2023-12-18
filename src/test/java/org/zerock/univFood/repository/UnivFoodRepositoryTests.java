package org.zerock.univFood.repository;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.univFood.entity.UnivFood;
import org.zerock.univFood.entity.UnivFoodImage;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class UnivFoodRepositoryTests {

    @Autowired
    private UnivFoodRepository univFoodRepository;
    @Autowired
    private UnivFoodImageRepository univFoodImageRepository;

//    @Commit
//    @Transactional
//    @Test
//    public void 음식점정보삽입(){
//
//        IntStream.rangeClosed(1,10).forEach(i->{
//            UnivFood univFood = UnivFood.builder()
//                    .restaurantName("음식점,,," +i)
//                    .build();
//
//            System.out.println("------------------------------");
//
//            univFoodRepository.save(univFood);
//
//            int count  = (int)(Math.random() * 5) + 1; // 1,2,3,4
//
//            for(int j = 0; j < count; j++){
//                UnivFoodImage univFoodImage = UnivFoodImage.builder()
//                        .uuid(UUID.randomUUID().toString())
//                        .univFood(univFood)
//                        .imgName("test"+j+".jpg")
//                        .build();
//                univFoodImageRepository.save(univFoodImage);
//            }
//            System.out.println("==============================");
//        });
//
//    }

    @Test
    public void testListPage(){
        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "uno"));
        Page<Object[]> result = univFoodRepository.getListPage(pageRequest);

        for(Object[] objects : result.getContent()){
            System.out.println(Arrays.toString(objects));
        }
    }
}
