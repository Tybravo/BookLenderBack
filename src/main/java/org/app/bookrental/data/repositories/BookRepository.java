package org.app.bookrental.data.repositories;

import org.app.bookrental.data.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface BookRepository  extends MongoRepository<Book, String> {
}
