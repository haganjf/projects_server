package edu.collin.db_conn.controller;

import edu.collin.db_conn.model.Category;
import edu.collin.db_conn.model.User;
import edu.collin.db_conn.repository.CategoryRepository;
import edu.collin.db_conn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository repository;

    @Autowired
    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/get")
    public List<Category> getAll() {
        return repository.findAll();
    }
}
