package edu.collin.db_conn.controller;

import edu.collin.db_conn.model.Item;
import edu.collin.db_conn.repository.ItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*") // Allow requests from all locations

@RequestMapping("/items")
public class ItemController {

    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Item> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        return repository.save(item);
    }
}
