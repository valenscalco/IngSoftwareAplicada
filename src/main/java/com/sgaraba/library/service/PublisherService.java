package com.sgaraba.library.service;

import com.sgaraba.library.domain.Publisher;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Publisher}.
 */
public interface PublisherService {
    /**
     * Save a publisher.
     *
     * @param publisher the entity to save.
     * @return the persisted entity.
     */
    Publisher save(Publisher publisher);

    /**
     * Updates a publisher.
     *
     * @param publisher the entity to update.
     * @return the persisted entity.
     */
    Publisher update(Publisher publisher);

    /**
     * Partially updates a publisher.
     *
     * @param publisher the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Publisher> partialUpdate(Publisher publisher);

    /**
     * Get all the publishers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Publisher> findAll(Pageable pageable);

    /**
     * Get the "id" publisher.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Publisher> findOne(Long id);

    /**
     * Delete the "id" publisher.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
