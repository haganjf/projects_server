package edu.collin.db_conn.repository;

import edu.collin.db_conn.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
