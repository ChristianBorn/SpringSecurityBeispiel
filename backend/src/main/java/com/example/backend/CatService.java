package com.example.backend;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository repository;

     List<Cat> findAll() {
         return repository.findAll();
     }

    public Cat save(Cat cat) {
        return repository.save(cat);
    }
}
