package com.sgaraba.library.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.sgaraba.library.domain.Book} entity. This class is used
 * in {@link com.sgaraba.library.web.rest.BookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter isbn;

    private StringFilter name;

    private StringFilter publishYear;

    private IntegerFilter copies;

    private LongFilter publisherId;

    private LongFilter authorId;

    private Boolean distinct;

    public BookCriteria() {}

    public BookCriteria(BookCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isbn = other.isbn == null ? null : other.isbn.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.publishYear = other.publishYear == null ? null : other.publishYear.copy();
        this.copies = other.copies == null ? null : other.copies.copy();
        this.publisherId = other.publisherId == null ? null : other.publisherId.copy();
        this.authorId = other.authorId == null ? null : other.authorId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BookCriteria copy() {
        return new BookCriteria(this);
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

    public StringFilter getIsbn() {
        return isbn;
    }

    public StringFilter isbn() {
        if (isbn == null) {
            isbn = new StringFilter();
        }
        return isbn;
    }

    public void setIsbn(StringFilter isbn) {
        this.isbn = isbn;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getPublishYear() {
        return publishYear;
    }

    public StringFilter publishYear() {
        if (publishYear == null) {
            publishYear = new StringFilter();
        }
        return publishYear;
    }

    public void setPublishYear(StringFilter publishYear) {
        this.publishYear = publishYear;
    }

    public IntegerFilter getCopies() {
        return copies;
    }

    public IntegerFilter copies() {
        if (copies == null) {
            copies = new IntegerFilter();
        }
        return copies;
    }

    public void setCopies(IntegerFilter copies) {
        this.copies = copies;
    }

    public LongFilter getPublisherId() {
        return publisherId;
    }

    public LongFilter publisherId() {
        if (publisherId == null) {
            publisherId = new LongFilter();
        }
        return publisherId;
    }

    public void setPublisherId(LongFilter publisherId) {
        this.publisherId = publisherId;
    }

    public LongFilter getAuthorId() {
        return authorId;
    }

    public LongFilter authorId() {
        if (authorId == null) {
            authorId = new LongFilter();
        }
        return authorId;
    }

    public void setAuthorId(LongFilter authorId) {
        this.authorId = authorId;
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
        final BookCriteria that = (BookCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(isbn, that.isbn) &&
            Objects.equals(name, that.name) &&
            Objects.equals(publishYear, that.publishYear) &&
            Objects.equals(copies, that.copies) &&
            Objects.equals(publisherId, that.publisherId) &&
            Objects.equals(authorId, that.authorId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, name, publishYear, copies, publisherId, authorId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (isbn != null ? "isbn=" + isbn + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (publishYear != null ? "publishYear=" + publishYear + ", " : "") +
            (copies != null ? "copies=" + copies + ", " : "") +
            (publisherId != null ? "publisherId=" + publisherId + ", " : "") +
            (authorId != null ? "authorId=" + authorId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
