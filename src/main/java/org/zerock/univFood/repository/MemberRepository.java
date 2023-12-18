package org.zerock.univFood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.univFood.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
