package com.example.backend.cat;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface CatRepository extends MongoRepository<Cat, String> {
}
