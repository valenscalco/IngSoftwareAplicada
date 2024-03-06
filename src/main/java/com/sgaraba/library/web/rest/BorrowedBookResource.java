package com.sgaraba.library.web.rest;

import com.sgaraba.library.domain.BorrowedBook;
import com.sgaraba.library.repository.BorrowedBookRepository;
import com.sgaraba.library.service.BorrowedBookQueryService;
import com.sgaraba.library.service.BorrowedBookService;
import com.sgaraba.library.service.criteria.BorrowedBookCriteria;
import com.sgaraba.library.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sgaraba.library.domain.BorrowedBook}.
 */
@RestController
@RequestMapping("/api")
public class BorrowedBookResource {

    private final Logger log = LoggerFactory.getLogger(BorrowedBookResource.class);

    private static final String ENTITY_NAME = "borrowedBook";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BorrowedBookService borrowedBookService;

    private final BorrowedBookRepository borrowedBookRepository;

    private final BorrowedBookQueryService borrowedBookQueryService;

    public BorrowedBookResource(
        BorrowedBookService borrowedBookService,
        BorrowedBookRepository borrowedBookRepository,
        BorrowedBookQueryService borrowedBookQueryService
    ) {
        this.borrowedBookService = borrowedBookService;
        this.borrowedBookRepository = borrowedBookRepository;
        this.borrowedBookQueryService = borrowedBookQueryService;
    }

    /**
     * {@code POST  /borrowed-books} : Create a new borrowedBook.
     *
     * @param borrowedBook the borrowedBook to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new borrowedBook, or with status {@code 400 (Bad Request)} if the borrowedBook has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/borrowed-books")
    public ResponseEntity<BorrowedBook> createBorrowedBook(@RequestBody BorrowedBook borrowedBook) throws URISyntaxException {
        log.debug("REST request to save BorrowedBook : {}", borrowedBook);
        if (borrowedBook.getId() != null) {
            throw new BadRequestAlertException("A new borrowedBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BorrowedBook result = borrowedBookService.save(borrowedBook);
        return ResponseEntity
            .created(new URI("/api/borrowed-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /borrowed-books/:id} : Updates an existing borrowedBook.
     *
     * @param id the id of the borrowedBook to save.
     * @param borrowedBook the borrowedBook to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated borrowedBook,
     * or with status {@code 400 (Bad Request)} if the borrowedBook is not valid,
     * or with status {@code 500 (Internal Server Error)} if the borrowedBook couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/borrowed-books/{id}")
    public ResponseEntity<BorrowedBook> updateBorrowedBook(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BorrowedBook borrowedBook
    ) throws URISyntaxException {
        log.debug("REST request to update BorrowedBook : {}, {}", id, borrowedBook);
        if (borrowedBook.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, borrowedBook.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!borrowedBookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BorrowedBook result = borrowedBookService.update(borrowedBook);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, borrowedBook.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /borrowed-books/:id} : Partial updates given fields of an existing borrowedBook, field will ignore if it is null
     *
     * @param id the id of the borrowedBook to save.
     * @param borrowedBook the borrowedBook to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated borrowedBook,
     * or with status {@code 400 (Bad Request)} if the borrowedBook is not valid,
     * or with status {@code 404 (Not Found)} if the borrowedBook is not found,
     * or with status {@code 500 (Internal Server Error)} if the borrowedBook couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/borrowed-books/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BorrowedBook> partialUpdateBorrowedBook(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BorrowedBook borrowedBook
    ) throws URISyntaxException {
        log.debug("REST request to partial update BorrowedBook partially : {}, {}", id, borrowedBook);
        if (borrowedBook.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, borrowedBook.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!borrowedBookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BorrowedBook> result = borrowedBookService.partialUpdate(borrowedBook);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, borrowedBook.getId().toString())
        );
    }

    /**
     * {@code GET  /borrowed-books} : get all the borrowedBooks.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of borrowedBooks in body.
     */
    @GetMapping("/borrowed-books")
    public ResponseEntity<List<BorrowedBook>> getAllBorrowedBooks(
        BorrowedBookCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BorrowedBooks by criteria: {}", criteria);
        Page<BorrowedBook> page = borrowedBookQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /borrowed-books/count} : count all the borrowedBooks.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/borrowed-books/count")
    public ResponseEntity<Long> countBorrowedBooks(BorrowedBookCriteria criteria) {
        log.debug("REST request to count BorrowedBooks by criteria: {}", criteria);
        return ResponseEntity.ok().body(borrowedBookQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /borrowed-books/:id} : get the "id" borrowedBook.
     *
     * @param id the id of the borrowedBook to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the borrowedBook, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/borrowed-books/{id}")
    public ResponseEntity<BorrowedBook> getBorrowedBook(@PathVariable Long id) {
        log.debug("REST request to get BorrowedBook : {}", id);
        Optional<BorrowedBook> borrowedBook = borrowedBookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(borrowedBook);
    }

    /**
     * {@code DELETE  /borrowed-books/:id} : delete the "id" borrowedBook.
     *
     * @param id the id of the borrowedBook to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/borrowed-books/{id}")
    public ResponseEntity<Void> deleteBorrowedBook(@PathVariable Long id) {
        log.debug("REST request to delete BorrowedBook : {}", id);
        borrowedBookService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
