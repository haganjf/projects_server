package edu.collin.db_conn.repository;

import edu.collin.db_conn.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select o.name,c.name,o.min_price, o.start_date, o.end_date from Item o JOIN Category  c on o.category_id = c.id")
    List<Item> findAllItems();
}
