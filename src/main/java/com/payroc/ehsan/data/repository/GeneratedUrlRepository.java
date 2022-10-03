package com.payroc.ehsan.data.repository;

import com.payroc.ehsan.data.entity.GeneratedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface GeneratedUrlRepository extends JpaRepository<GeneratedUrl, Long> {

    @Query("SELECT t FROM GeneratedUrl t WHERE t.id >= ?1 AND t.id < ?2 AND t.version=0")
    @Lock(LockModeType.OPTIMISTIC)
    List<GeneratedUrl> getBulkGeneratedUrl(Long start, Long end);

}
