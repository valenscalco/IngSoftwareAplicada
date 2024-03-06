package com.sgaraba.library.service.impl;

import com.sgaraba.library.domain.BorrowedBook;
import com.sgaraba.library.repository.BorrowedBookRepository;
import com.sgaraba.library.service.BorrowedBookService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BorrowedBook}.
 */
@Service
@Transactional
public class BorrowedBookServiceImpl implements BorrowedBookService {

    private final Logger log = LoggerFactory.getLogger(BorrowedBookServiceImpl.class);

    private final BorrowedBookRepository borrowedBookRepository;

    public BorrowedBookServiceImpl(BorrowedBookRepository borrowedBookRepository) {
        this.borrowedBookRepository = borrowedBookRepository;
    }

    @Override
    public BorrowedBook save(BorrowedBook borrowedBook) {
        log.debug("Request to save BorrowedBook : {}", borrowedBook);
        return borrowedBookRepository.save(borrowedBook);
    }

    @Override
    public BorrowedBook update(BorrowedBook borrowedBook) {
        log.debug("Request to update BorrowedBook : {}", borrowedBook);
        return borrowedBookRepository.save(borrowedBook);
    }

    @Override
    public Optional<BorrowedBook> partialUpdate(BorrowedBook borrowedBook) {
        log.debug("Request to partially update BorrowedBook : {}", borrowedBook);

        return borrowedBookRepository
            .findById(borrowedBook.getId())
            .map(existingBorrowedBook -> {
                if (borrowedBook.getBorrowDate() != null) {
                    existingBorrowedBook.setBorrowDate(borrowedBook.getBorrowDate());
                }

                return existingBorrowedBook;
            })
            .map(borrowedBookRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BorrowedBook> findAll(Pageable pageable) {
        log.debug("Request to get all BorrowedBooks");
        return borrowedBookRepository.findAll(pageable);
    }

    public Page<BorrowedBook> findAllWithEagerRelationships(Pageable pageable) {
        return borrowedBookRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BorrowedBook> findOne(Long id) {
        log.debug("Request to get BorrowedBook : {}", id);
        return borrowedBookRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BorrowedBook : {}", id);
        borrowedBookRepository.deleteById(id);
    }
}
