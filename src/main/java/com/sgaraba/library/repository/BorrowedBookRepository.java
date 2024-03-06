package com.sgaraba.library.repository;

import com.sgaraba.library.domain.BorrowedBook;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BorrowedBook entity.
 */
@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long>, JpaSpecificationExecutor<BorrowedBook> {
    default Optional<BorrowedBook> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BorrowedBook> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BorrowedBook> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct borrowedBook from BorrowedBook borrowedBook left join fetch borrowedBook.book left join fetch borrowedBook.client",
        countQuery = "select count(distinct borrowedBook) from BorrowedBook borrowedBook"
    )
    Page<BorrowedBook> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct borrowedBook from BorrowedBook borrowedBook left join fetch borrowedBook.book left join fetch borrowedBook.client"
    )
    List<BorrowedBook> findAllWithToOneRelationships();

    @Query(
        "select borrowedBook from BorrowedBook borrowedBook left join fetch borrowedBook.book left join fetch borrowedBook.client where borrowedBook.id =:id"
    )
    Optional<BorrowedBook> findOneWithToOneRelationships(@Param("id") Long id);
}
