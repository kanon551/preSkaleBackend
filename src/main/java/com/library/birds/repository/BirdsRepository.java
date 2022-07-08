package com.library.birds.repository;

import com.library.birds.model.Birds;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BirdsRepository extends MongoRepository<Birds,String> {

    public Birds findByCommonName(String commonName);
}
