package com.payroc.ehsan.service.impl;

import com.payroc.ehsan.data.entity.ShortUrl;
import com.payroc.ehsan.data.repository.ShortUrlRepository;
import com.payroc.ehsan.util.Constants;
import com.payroc.ehsan.util.GeneratedUrlManager;
import com.payroc.ehsan.service.ShortUrlService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    ShortUrlRepository shortUrlRepository;
    public ShortUrlServiceImpl(ShortUrlRepository shortUrlRepository){
        this.shortUrlRepository=shortUrlRepository;
    }

    /**
     * generate or assign a shorted url to a long url and save this assignment in the db
     * 
     */
    @Override
    @Transactional
    public String getShortedUrl(String originalUrlStr, String suggestedUrl) {
    	ShortUrl shortUrl;
    	if(suggestedUrl!=null && suggestedUrl.length()==Constants.SHORT_URL_LENGTH) {
            shortUrl=new ShortUrl(originalUrlStr, suggestedUrl);
    	}else {
    		String shortUrlStr = GeneratedUrlManager.getGeneratedUrlForConsumption().getShortUrl();
    		shortUrl=new ShortUrl(originalUrlStr, shortUrlStr);
    	}
        shortUrlRepository.save(shortUrl);
        return shortUrl.getShortUrl();
    }
    
    /**
     * Cached service for mapping a shorted url to a long url for end user 
     * 
     */
    @Override
    @Cacheable(
  	      value = "shortUrlCache", 
  	      key = "#shortUrl")
    public String getFullUrl(String shortUrl) {
        List<ShortUrl> matchedEntities = shortUrlRepository.getFullUrlFromShort(shortUrl);
        if(matchedEntities!=null&&matchedEntities.size()>0)
            return matchedEntities.get(0).getFullUrl();
        throw new RuntimeException("invalid short url");
    }
}
