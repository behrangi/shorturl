package com.payroc.ehsan.config;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.payroc.ehsan.service.ShortUrlService;
import com.payroc.ehsan.util.Constants;

/**
 * A simple integration test just for proof of concept!
 * It needs at least 50 more tests that I can call it a test suite
 * @author ehsan
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:test-application.properties")
class ShorturlApplicationTests {
   
    
    @Autowired
    ShortUrlService shortUrlService;
    String testUrl = "http://www.yahoo.com/test";
    
    @Test
    void testShortUrlService() {
    	 String shortedUrlResult = shortUrlService.getShortedUrl(testUrl, null);
    	 assertTrue(shortedUrlResult!=null);
         assertTrue(shortedUrlResult.length()==Constants.SHORT_URL_LENGTH);
         String fullUrlStr = shortUrlService.getFullUrl(shortedUrlResult);
    	 assertTrue(fullUrlStr.equals(testUrl));
    }

}
