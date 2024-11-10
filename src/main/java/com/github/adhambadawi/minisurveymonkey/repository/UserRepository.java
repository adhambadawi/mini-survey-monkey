package com.github.adhambadawi.minisurveymonkey.repository;

import com.github.adhambadawi.minisurveymonkey.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
