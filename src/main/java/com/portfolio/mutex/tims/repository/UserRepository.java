package com.portfolio.mutex.tims.repository;

import com.portfolio.mutex.tims.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Finds user by email
   *
   * @param email the email
   * @return the {@link Optional} {@link User}
   */
  Optional<User> findByEmail(String email);
}
