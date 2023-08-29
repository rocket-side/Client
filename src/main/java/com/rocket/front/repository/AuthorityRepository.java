package com.rocket.front.repository;

import com.jeongm.rocketsecurity.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,Integer> {
}
