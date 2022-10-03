package com.payroc.ehsan.data.entity;


import javax.persistence.*;

import com.payroc.ehsan.util.Constants;

@Entity
@Table(name = "g_url", indexes = @Index(name = "url_index", columnList = "short_url"))
public class GeneratedUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="short_url", nullable = false, length = Constants.SHORT_URL_LENGTH, unique = true)
    private String shortUrl;

    @Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version = 0L;

    /**
     * to count view stats of each shorted url in the future
     */
    @Column(name = "view", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long view;

    public GeneratedUrl() {

    }


    public Long getId() {
        return id;
    }

    public GeneratedUrl(String shortUrl){
        version = 0L;
        this.shortUrl = shortUrl;
        view = 0L;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
