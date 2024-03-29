package org.zerock.univFood.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.univFood.entity.Member;
import org.zerock.univFood.entity.Review;
import org.zerock.univFood.entity.UnivFood;
import org.zerock.univFood.entity.UnivFoodImage;

import java.util.List;

public interface UnivFoodImageRepository extends JpaRepository<UnivFoodImage, Long> {

//    @EntityGraph(attributePaths = {"univFood"}, type = EntityGraph.EntityGraphType.FETCH)
//    List<Object> findByUnivFood(UnivFood univFood);


    @Modifying
    @Transactional
    @Query("delete from UnivFoodImage ui where ui.univFood = :univFood")
    void deleteByUnivFoodImage(UnivFood univFood);   // UnivFood의 Id값을 전달하여 외래키값이 같은 이미지들을 삭제
}
