package org.zerock.univFood.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.univFood.entity.UnivFood;

public interface UnivFoodRepository extends JpaRepository<UnivFood, Long> {
    @Query("select u, ui, avg(coalesce(r.grade,0)), count(distinct r) from UnivFood u "
            +"left outer join UnivFoodImage ui on ui.univFood = u "
            +"left outer join Review r on r.univFood = u group by u")
    Page<Object[]> getListPage(Pageable pageable);
}
