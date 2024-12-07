package org.app.booklender.dtos.requests;

import lombok.Data;
import org.app.booklender.data.models.Book;
import org.app.booklender.data.models.Member;

import java.util.List;

@Data
public class AddShelveRequest {
    private String bookId;
    private String memberId;
    private List<Book> bookIds;
    private List<Member> memberIds;
}
