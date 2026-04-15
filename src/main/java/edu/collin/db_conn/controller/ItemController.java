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

    @PostMapping("/create")
    public Item create(@RequestBody Item item) {
        System.out.println("Name: "+item.getName());
        System.out.println("Category: "+item.getCategory_id());
        System.out.println("Description: "+item.getDescription());
        System.out.println("Min price: "+item.getMin_price());
        System.out.println("Start Date: "+item.getStart_date());
        System.out.println("End Date: "+item.getEnd_date());
        return repository.save(item);
    }
}
