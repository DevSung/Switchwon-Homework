package com.example.switchwon.domain.repository;

import com.example.switchwon.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
