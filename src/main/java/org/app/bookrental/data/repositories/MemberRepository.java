package org.app.bookrental.data.repositories;

import org.app.bookrental.data.models.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {
    Member findByEmail(String emailAddy);
    Member findByPassword(String password);

    //Optional<Member> findByEmail(String email);

}
