package com.occultus.learncase.idempotent.repo;

import com.occultus.learncase.idempotent.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    @Query(value = "SELECT * FROM Test WHERE id = :id For UPDATE", nativeQuery = true)
    Test selectForUpdate(@Param("id") Integer id);

    @Query(value = "SELECT * FROM Test WHERE id = :id", nativeQuery = true)
    Test selectNoLock(@Param("id") Integer id);
}
