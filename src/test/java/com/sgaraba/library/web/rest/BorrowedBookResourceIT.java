package com.sgaraba.library.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sgaraba.library.IntegrationTest;
import com.sgaraba.library.domain.Book;
import com.sgaraba.library.domain.BorrowedBook;
import com.sgaraba.library.domain.Client;
import com.sgaraba.library.repository.BorrowedBookRepository;
import com.sgaraba.library.service.BorrowedBookService;
import com.sgaraba.library.service.criteria.BorrowedBookCriteria;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BorrowedBookResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BorrowedBookResourceIT {

    private static final LocalDate DEFAULT_BORROW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BORROW_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BORROW_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/borrowed-books";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BorrowedBookRepository borrowedBookRepository;

    @Mock
    private BorrowedBookRepository borrowedBookRepositoryMock;

    @Mock
    private BorrowedBookService borrowedBookServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBorrowedBookMockMvc;

    private BorrowedBook borrowedBook;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BorrowedBook createEntity(EntityManager em) {
        BorrowedBook borrowedBook = new BorrowedBook().borrowDate(DEFAULT_BORROW_DATE);
        return borrowedBook;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BorrowedBook createUpdatedEntity(EntityManager em) {
        BorrowedBook borrowedBook = new BorrowedBook().borrowDate(UPDATED_BORROW_DATE);
        return borrowedBook;
    }

    @BeforeEach
    public void initTest() {
        borrowedBook = createEntity(em);
    }

    @Test
    @Transactional
    void createBorrowedBook() throws Exception {
        int databaseSizeBeforeCreate = borrowedBookRepository.findAll().size();
        // Create the BorrowedBook
        restBorrowedBookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borrowedBook)))
            .andExpect(status().isCreated());

        // Validate the BorrowedBook in the database
        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        assertThat(borrowedBookList).hasSize(databaseSizeBeforeCreate + 1);
        BorrowedBook testBorrowedBook = borrowedBookList.get(borrowedBookList.size() - 1);
        assertThat(testBorrowedBook.getBorrowDate()).isEqualTo(DEFAULT_BORROW_DATE);
    }

    @Test
    @Transactional
    void createBorrowedBookWithExistingId() throws Exception {
        // Create the BorrowedBook with an existing ID
        borrowedBook.setId(1L);

        int databaseSizeBeforeCreate = borrowedBookRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBorrowedBookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borrowedBook)))
            .andExpect(status().isBadRequest());

        // Validate the BorrowedBook in the database
        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        assertThat(borrowedBookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBorrowedBooks() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        // Get all the borrowedBookList
        restBorrowedBookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borrowedBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].borrowDate").value(hasItem(DEFAULT_BORROW_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBorrowedBooksWithEagerRelationshipsIsEnabled() throws Exception {
        when(borrowedBookServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBorrowedBookMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(borrowedBookServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBorrowedBooksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(borrowedBookServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBorrowedBookMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(borrowedBookRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getBorrowedBook() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        // Get the borrowedBook
        restBorrowedBookMockMvc
            .perform(get(ENTITY_API_URL_ID, borrowedBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(borrowedBook.getId().intValue()))
            .andExpect(jsonPath("$.borrowDate").value(DEFAULT_BORROW_DATE.toString()));
    }

    @Test
    @Transactional
    void getBorrowedBooksByIdFiltering() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        Long id = borrowedBook.getId();

        defaultBorrowedBookShouldBeFound("id.equals=" + id);
        defaultBorrowedBookShouldNotBeFound("id.notEquals=" + id);

        defaultBorrowedBookShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBorrowedBookShouldNotBeFound("id.greaterThan=" + id);

        defaultBorrowedBookShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBorrowedBookShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBorrowedBooksByBorrowDateIsEqualToSomething() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        // Get all the borrowedBookList where borrowDate equals to DEFAULT_BORROW_DATE
        defaultBorrowedBookShouldBeFound("borrowDate.equals=" + DEFAULT_BORROW_DATE);

        // Get all the borrowedBookList where borrowDate equals to UPDATED_BORROW_DATE
        defaultBorrowedBookShouldNotBeFound("borrowDate.equals=" + UPDATED_BORROW_DATE);
    }

    @Test
    @Transactional
    void getAllBorrowedBooksByBorrowDateIsInShouldWork() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        // Get all the borrowedBookList where borrowDate in DEFAULT_BORROW_DATE or UPDATED_BORROW_DATE
        defaultBorrowedBookShouldBeFound("borrowDate.in=" + DEFAULT_BORROW_DATE + "," + UPDATED_BORROW_DATE);

        // Get all the borrowedBookList where borrowDate equals to UPDATED_BORROW_DATE
        defaultBorrowedBookShouldNotBeFound("borrowDate.in=" + UPDATED_BORROW_DATE);
    }

    @Test
    @Transactional
    void getAllBorrowedBooksByBorrowDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        // Get all the borrowedBookList where borrowDate is not null
        defaultBorrowedBookShouldBeFound("borrowDate.specified=true");

        // Get all the borrowedBookList where borrowDate is null
        defaultBorrowedBookShouldNotBeFound("borrowDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBorrowedBooksByBorrowDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        // Get all the borrowedBookList where borrowDate is greater than or equal to DEFAULT_BORROW_DATE
        defaultBorrowedBookShouldBeFound("borrowDate.greaterThanOrEqual=" + DEFAULT_BORROW_DATE);

        // Get all the borrowedBookList where borrowDate is greater than or equal to UPDATED_BORROW_DATE
        defaultBorrowedBookShouldNotBeFound("borrowDate.greaterThanOrEqual=" + UPDATED_BORROW_DATE);
    }

    @Test
    @Transactional
    void getAllBorrowedBooksByBorrowDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        // Get all the borrowedBookList where borrowDate is less than or equal to DEFAULT_BORROW_DATE
        defaultBorrowedBookShouldBeFound("borrowDate.lessThanOrEqual=" + DEFAULT_BORROW_DATE);

        // Get all the borrowedBookList where borrowDate is less than or equal to SMALLER_BORROW_DATE
        defaultBorrowedBookShouldNotBeFound("borrowDate.lessThanOrEqual=" + SMALLER_BORROW_DATE);
    }

    @Test
    @Transactional
    void getAllBorrowedBooksByBorrowDateIsLessThanSomething() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        // Get all the borrowedBookList where borrowDate is less than DEFAULT_BORROW_DATE
        defaultBorrowedBookShouldNotBeFound("borrowDate.lessThan=" + DEFAULT_BORROW_DATE);

        // Get all the borrowedBookList where borrowDate is less than UPDATED_BORROW_DATE
        defaultBorrowedBookShouldBeFound("borrowDate.lessThan=" + UPDATED_BORROW_DATE);
    }

    @Test
    @Transactional
    void getAllBorrowedBooksByBorrowDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        // Get all the borrowedBookList where borrowDate is greater than DEFAULT_BORROW_DATE
        defaultBorrowedBookShouldNotBeFound("borrowDate.greaterThan=" + DEFAULT_BORROW_DATE);

        // Get all the borrowedBookList where borrowDate is greater than SMALLER_BORROW_DATE
        defaultBorrowedBookShouldBeFound("borrowDate.greaterThan=" + SMALLER_BORROW_DATE);
    }

    @Test
    @Transactional
    void getAllBorrowedBooksByBookIsEqualToSomething() throws Exception {
        Book book;
        if (TestUtil.findAll(em, Book.class).isEmpty()) {
            borrowedBookRepository.saveAndFlush(borrowedBook);
            book = BookResourceIT.createEntity(em);
        } else {
            book = TestUtil.findAll(em, Book.class).get(0);
        }
        em.persist(book);
        em.flush();
        borrowedBook.setBook(book);
        borrowedBookRepository.saveAndFlush(borrowedBook);
        Long bookId = book.getId();

        // Get all the borrowedBookList where book equals to bookId
        defaultBorrowedBookShouldBeFound("bookId.equals=" + bookId);

        // Get all the borrowedBookList where book equals to (bookId + 1)
        defaultBorrowedBookShouldNotBeFound("bookId.equals=" + (bookId + 1));
    }

    @Test
    @Transactional
    void getAllBorrowedBooksByClientIsEqualToSomething() throws Exception {
        Client client;
        if (TestUtil.findAll(em, Client.class).isEmpty()) {
            borrowedBookRepository.saveAndFlush(borrowedBook);
            client = ClientResourceIT.createEntity(em);
        } else {
            client = TestUtil.findAll(em, Client.class).get(0);
        }
        em.persist(client);
        em.flush();
        borrowedBook.setClient(client);
        borrowedBookRepository.saveAndFlush(borrowedBook);
        Long clientId = client.getId();

        // Get all the borrowedBookList where client equals to clientId
        defaultBorrowedBookShouldBeFound("clientId.equals=" + clientId);

        // Get all the borrowedBookList where client equals to (clientId + 1)
        defaultBorrowedBookShouldNotBeFound("clientId.equals=" + (clientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBorrowedBookShouldBeFound(String filter) throws Exception {
        restBorrowedBookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(borrowedBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].borrowDate").value(hasItem(DEFAULT_BORROW_DATE.toString())));

        // Check, that the count call also returns 1
        restBorrowedBookMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBorrowedBookShouldNotBeFound(String filter) throws Exception {
        restBorrowedBookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBorrowedBookMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBorrowedBook() throws Exception {
        // Get the borrowedBook
        restBorrowedBookMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBorrowedBook() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        int databaseSizeBeforeUpdate = borrowedBookRepository.findAll().size();

        // Update the borrowedBook
        BorrowedBook updatedBorrowedBook = borrowedBookRepository.findById(borrowedBook.getId()).get();
        // Disconnect from session so that the updates on updatedBorrowedBook are not directly saved in db
        em.detach(updatedBorrowedBook);
        updatedBorrowedBook.borrowDate(UPDATED_BORROW_DATE);

        restBorrowedBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBorrowedBook.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBorrowedBook))
            )
            .andExpect(status().isOk());

        // Validate the BorrowedBook in the database
        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        assertThat(borrowedBookList).hasSize(databaseSizeBeforeUpdate);
        BorrowedBook testBorrowedBook = borrowedBookList.get(borrowedBookList.size() - 1);
        assertThat(testBorrowedBook.getBorrowDate()).isEqualTo(UPDATED_BORROW_DATE);
    }

    @Test
    @Transactional
    void putNonExistingBorrowedBook() throws Exception {
        int databaseSizeBeforeUpdate = borrowedBookRepository.findAll().size();
        borrowedBook.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBorrowedBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, borrowedBook.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(borrowedBook))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedBook in the database
        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        assertThat(borrowedBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBorrowedBook() throws Exception {
        int databaseSizeBeforeUpdate = borrowedBookRepository.findAll().size();
        borrowedBook.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorrowedBookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(borrowedBook))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedBook in the database
        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        assertThat(borrowedBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBorrowedBook() throws Exception {
        int databaseSizeBeforeUpdate = borrowedBookRepository.findAll().size();
        borrowedBook.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorrowedBookMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(borrowedBook)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BorrowedBook in the database
        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        assertThat(borrowedBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBorrowedBookWithPatch() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        int databaseSizeBeforeUpdate = borrowedBookRepository.findAll().size();

        // Update the borrowedBook using partial update
        BorrowedBook partialUpdatedBorrowedBook = new BorrowedBook();
        partialUpdatedBorrowedBook.setId(borrowedBook.getId());

        restBorrowedBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBorrowedBook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBorrowedBook))
            )
            .andExpect(status().isOk());

        // Validate the BorrowedBook in the database
        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        assertThat(borrowedBookList).hasSize(databaseSizeBeforeUpdate);
        BorrowedBook testBorrowedBook = borrowedBookList.get(borrowedBookList.size() - 1);
        assertThat(testBorrowedBook.getBorrowDate()).isEqualTo(DEFAULT_BORROW_DATE);
    }

    @Test
    @Transactional
    void fullUpdateBorrowedBookWithPatch() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        int databaseSizeBeforeUpdate = borrowedBookRepository.findAll().size();

        // Update the borrowedBook using partial update
        BorrowedBook partialUpdatedBorrowedBook = new BorrowedBook();
        partialUpdatedBorrowedBook.setId(borrowedBook.getId());

        partialUpdatedBorrowedBook.borrowDate(UPDATED_BORROW_DATE);

        restBorrowedBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBorrowedBook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBorrowedBook))
            )
            .andExpect(status().isOk());

        // Validate the BorrowedBook in the database
        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        assertThat(borrowedBookList).hasSize(databaseSizeBeforeUpdate);
        BorrowedBook testBorrowedBook = borrowedBookList.get(borrowedBookList.size() - 1);
        assertThat(testBorrowedBook.getBorrowDate()).isEqualTo(UPDATED_BORROW_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingBorrowedBook() throws Exception {
        int databaseSizeBeforeUpdate = borrowedBookRepository.findAll().size();
        borrowedBook.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBorrowedBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, borrowedBook.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(borrowedBook))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedBook in the database
        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        assertThat(borrowedBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBorrowedBook() throws Exception {
        int databaseSizeBeforeUpdate = borrowedBookRepository.findAll().size();
        borrowedBook.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorrowedBookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(borrowedBook))
            )
            .andExpect(status().isBadRequest());

        // Validate the BorrowedBook in the database
        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        assertThat(borrowedBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBorrowedBook() throws Exception {
        int databaseSizeBeforeUpdate = borrowedBookRepository.findAll().size();
        borrowedBook.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBorrowedBookMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(borrowedBook))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BorrowedBook in the database
        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        assertThat(borrowedBookList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBorrowedBook() throws Exception {
        // Initialize the database
        borrowedBookRepository.saveAndFlush(borrowedBook);

        int databaseSizeBeforeDelete = borrowedBookRepository.findAll().size();

        // Delete the borrowedBook
        restBorrowedBookMockMvc
            .perform(delete(ENTITY_API_URL_ID, borrowedBook.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BorrowedBook> borrowedBookList = borrowedBookRepository.findAll();
        assertThat(borrowedBookList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
