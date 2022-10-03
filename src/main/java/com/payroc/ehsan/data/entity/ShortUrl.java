package com.payroc.ehsan.data.entity;


import javax.persistence.*;

import com.payroc.ehsan.util.Constants;

import java.sql.Timestamp;

@Entity
@Table(name = "url")
public class ShortUrl {
    /**
     * since id is short url it does not need separated index
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "short_url", length = Constants.SHORT_URL_LENGTH, nullable = false, unique=true)
    private String shortUrl;

    @Column(name="full_url", length = 2048)
    private String fullUrl;

    @Column(name="expiration_date")
    private Timestamp expirationDate;

    @Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version = 0L;

    public ShortUrl(){

    }
    public ShortUrl(String fullUrl, String shortUrl){
        this.fullUrl=fullUrl;
        this.shortUrl=shortUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Http Get request length limit = 2048
     */
    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
