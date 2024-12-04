package org.app.bookrental.dtos.requests;

import lombok.Data;
import org.app.bookrental.data.models.Book;
import org.app.bookrental.data.models.Member;

import java.util.List;

@Data
public class AddShelveRequest {
    private String bookId;
    private String memberId;
    private List<Book> bookIds;
    private List<Member> memberIds;
}
