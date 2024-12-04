package org.app.bookrental.data.repositories;

import org.app.bookrental.data.models.Shelve;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface ShelveRepository extends MongoRepository<Shelve, String> {
}