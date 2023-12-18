package org.zerock.univFood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.univFood.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
