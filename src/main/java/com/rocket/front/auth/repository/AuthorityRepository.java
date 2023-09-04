package com.rocket.front.auth.repository;

import com.rocket.front.auth.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,Integer> {
}
