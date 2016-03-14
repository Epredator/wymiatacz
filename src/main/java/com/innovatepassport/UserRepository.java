package com.innovatepassport;

import com.innovatepassport.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByLastNameStartsWithIgnoreCase(String lastName);
}
