package com.assginment.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assginment.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.email =:email")
	public User getUserByEmail(@Param("email") String email);

	boolean existsByEmail(String email);

	@Modifying
	@Query("UPDATE User u SET u.password = :newPassword WHERE u.email = :userEmail")
	void updatePassword(@Param("userEmail") String userEmail, @Param("newPassword") String newPassword);

}
