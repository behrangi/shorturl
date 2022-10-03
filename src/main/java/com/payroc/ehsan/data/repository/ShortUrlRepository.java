package com.payroc.ehsan.data.repository;

import com.payroc.ehsan.data.entity.ShortUrl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    @Query("SELECT t FROM ShortUrl t WHERE t.shortUrl like ?1")
    public List<ShortUrl> getFullUrlFromShort(String shortUrl);
}
