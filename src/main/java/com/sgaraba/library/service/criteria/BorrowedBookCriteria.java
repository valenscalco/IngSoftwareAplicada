package com.sgaraba.library.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.sgaraba.library.domain.BorrowedBook} entity. This class is used
 * in {@link com.sgaraba.library.web.rest.BorrowedBookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /borrowed-books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BorrowedBookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter borrowDate;

    private LongFilter bookId;

    private LongFilter clientId;

    private Boolean distinct;

    public BorrowedBookCriteria() {}

    public BorrowedBookCriteria(BorrowedBookCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.borrowDate = other.borrowDate == null ? null : other.borrowDate.copy();
        this.bookId = other.bookId == null ? null : other.bookId.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BorrowedBookCriteria copy() {
        return new BorrowedBookCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getBorrowDate() {
        return borrowDate;
    }

    public LocalDateFilter borrowDate() {
        if (borrowDate == null) {
            borrowDate = new LocalDateFilter();
        }
        return borrowDate;
    }

    public void setBorrowDate(LocalDateFilter borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LongFilter getBookId() {
        return bookId;
    }

    public LongFilter bookId() {
        if (bookId == null) {
            bookId = new LongFilter();
        }
        return bookId;
    }

    public void setBookId(LongFilter bookId) {
        this.bookId = bookId;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public LongFilter clientId() {
        if (clientId == null) {
            clientId = new LongFilter();
        }
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BorrowedBookCriteria that = (BorrowedBookCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(borrowDate, that.borrowDate) &&
            Objects.equals(bookId, that.bookId) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, borrowDate, bookId, clientId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BorrowedBookCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (borrowDate != null ? "borrowDate=" + borrowDate + ", " : "") +
            (bookId != null ? "bookId=" + bookId + ", " : "") +
            (clientId != null ? "clientId=" + clientId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
