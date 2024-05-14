package com.valenscalco.library.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.valenscalco.library.domain.BorrowedBook} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BorrowedBookDTO implements Serializable {

    private Long id;

    private LocalDate borrowDate;

    private BookDTO book;

    private ClientDTO client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BorrowedBookDTO)) {
            return false;
        }

        BorrowedBookDTO borrowedBookDTO = (BorrowedBookDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, borrowedBookDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BorrowedBookDTO{" +
            "id=" + getId() +
            ", borrowDate='" + getBorrowDate() + "'" +
            ", book=" + getBook() +
            ", client=" + getClient() +
            "}";
    }
}
