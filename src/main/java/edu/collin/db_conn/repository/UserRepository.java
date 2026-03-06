package edu.collin.db_conn.repository;

import edu.collin.db_conn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}