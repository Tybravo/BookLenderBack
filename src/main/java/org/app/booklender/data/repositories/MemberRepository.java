package org.app.booklender.data.repositories;

import org.app.booklender.data.models.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends MongoRepository<Member, String> {
    Member findByEmail(String emailAddy);
    Member findByPassword(String password);

    //Optional<Member> findByEmail(String email);

}
