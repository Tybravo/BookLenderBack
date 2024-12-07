package org.app.booklender.data.repositories;

import org.app.booklender.data.models.Shelve;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ShelveRepository extends MongoRepository<Shelve, String> {
}