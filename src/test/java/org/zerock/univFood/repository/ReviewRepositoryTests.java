package org.zerock.univFood.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.univFood.entity.Member;
import org.zerock.univFood.entity.Review;
import org.zerock.univFood.entity.UnivFood;

import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void 음식점리뷰삽입(){
        IntStream.rangeClosed(1,20).forEach(i->{
            Long uno = (long)(Math.random()*10)+1;

            Long mid = ((long)(Math.random()*10)+1);
            Member member = Member.builder().mid(mid).build();

            Review foodReview = Review.builder()
                    .member(member) //회원 id 적용된 상태
                    .univFood(UnivFood.builder().uno(uno).build())
                    .grade((int)(Math.random()*5) + 1)
                    .text("맛 평가,,,"+i)
                    .build();

            reviewRepository.save(foodReview);
        });
    }
}
