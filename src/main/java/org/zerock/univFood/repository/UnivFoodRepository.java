package org.zerock.univFood.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.univFood.entity.UnivFood;

import java.util.List;

public interface UnivFoodRepository extends JpaRepository<UnivFood, Long> {

    // UnivFood객체, UnivFoodImage객체, double 값으로 나오는 식당의 평점, Lond타입 리뷰 개수를 Object[]로 반환
    @Query("select u, ui, avg(coalesce(r.grade,0)), count(distinct r) from UnivFood u "
            +"left outer join UnivFoodImage ui on ui.univFood = u "
            +"left outer join Review r on r.univFood = u group by u")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select u, ui, avg(coalesce(r.grade,0)), count(distinct(r)) "
            +" from UnivFood u left outer join UnivFoodImage ui on ui.univFood = u "
            +" left outer join Review r on r.univFood = u "
            +" where u.uno = :uno group by ui")
    List<Object[]> getUnivFoodWithAll(Long uno); // 특정 음식점 조회
}
