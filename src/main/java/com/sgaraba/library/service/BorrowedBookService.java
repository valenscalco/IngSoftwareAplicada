package com.sgaraba.library.service;

import com.sgaraba.library.domain.BorrowedBook;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BorrowedBook}.
 */
public interface BorrowedBookService {
    /**
     * Save a borrowedBook.
     *
     * @param borrowedBook the entity to save.
     * @return the persisted entity.
     */
    BorrowedBook save(BorrowedBook borrowedBook);

    /**
     * Updates a borrowedBook.
     *
     * @param borrowedBook the entity to update.
     * @return the persisted entity.
     */
    BorrowedBook update(BorrowedBook borrowedBook);

    /**
     * Partially updates a borrowedBook.
     *
     * @param borrowedBook the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BorrowedBook> partialUpdate(BorrowedBook borrowedBook);

    /**
     * Get all the borrowedBooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BorrowedBook> findAll(Pageable pageable);

    /**
     * Get all the borrowedBooks with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BorrowedBook> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" borrowedBook.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BorrowedBook> findOne(Long id);

    /**
     * Delete the "id" borrowedBook.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
