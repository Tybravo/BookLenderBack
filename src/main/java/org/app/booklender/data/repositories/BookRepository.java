package org.app.booklender.data.repositories;

import org.app.booklender.data.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BookRepository  extends MongoRepository<Book, String> {
}
