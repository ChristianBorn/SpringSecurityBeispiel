package com.example.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cats")
@RequiredArgsConstructor
public class CatController {

    private final CatService service;


    @GetMapping
    List<Cat> getAllCats() {
        return service.findAll();
    }

    @PostMapping
    Cat createNewCat(@RequestBody Cat cat) {
        return service.save(cat);
    }

}
