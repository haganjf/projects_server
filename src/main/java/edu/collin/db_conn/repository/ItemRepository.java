package edu.collin.db_conn.repository;

import edu.collin.db_conn.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
