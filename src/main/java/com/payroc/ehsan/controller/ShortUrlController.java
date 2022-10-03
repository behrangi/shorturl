package com.payroc.ehsan.controller;

import com.payroc.ehsan.service.ShortUrlService;
import com.payroc.ehsan.util.Constants;
import exception.InvalidLongUrlException;
import exception.InvalidShortUrlException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/m")
public class ShortUrlController {

    ShortUrlService shortUrlService;
    public ShortUrlController(ShortUrlService shortUrlService){
        this.shortUrlService=shortUrlService;
    }

    /**
     * It receives a short url and return a redirect navigation to the main website which this short url represent
     * 
     * @param path short path for the main long url
     * @param response send a http redirect for end user's ui usage! It navigates end user to main website
     * @throws Exception
     */
    @RequestMapping(value = "/{path}", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8", method = RequestMethod.GET)
    public void getRealUrlPage(@PathVariable(value = "path", required = true) String path, HttpServletResponse response) throws Exception {
    	response.sendRedirect(getRealUrlCommon(path));
    }

    /**
     * rest implementation of @getRealUrlPage service which receive just the main url without redirect navigation
     * @param path
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/api/v1/{path}", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8", method = RequestMethod.GET)
    public String getRealUrl(@PathVariable(value = "path", required = true) String path) throws Exception {
        return getRealUrlCommon(path);
    }

    /**
     * 
     * @param url main long url which end user wants to short it
     * @param sug_url suggested not duplicated short url by the end user
     * @param httpRequest
     * @return a ready to use shorted url !
     * @throws Exception contains error message
     */
    @RequestMapping(value = "/api/v1/short", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8", method = RequestMethod.GET)
    public String shortThisUrl(@RequestParam(value = "url", required = true) String url, @RequestParam(value = "sug_url", required = false) String sug_url, HttpServletRequest httpRequest) throws Exception {
        if(url==null || url.length()<4)
        	throw new InvalidLongUrlException();
        if(sug_url!=null && sug_url.length()>0)
        	if(sug_url.length()!=Constants.SHORT_URL_LENGTH)
        		throw new InvalidShortUrlException();
        url = URLDecoder.decode(url, "UTF-8");
        StringBuilder response = new StringBuilder("http://").append(httpRequest.getServerName()).append(":").append(httpRequest.getServerPort())
        		.append("/m/").append(shortUrlService.getShortedUrl(url, sug_url));
    	return response.toString();
    }
    
    private String getRealUrlCommon(String path) {
    	if(path==null || path.length()!=Constants.SHORT_URL_LENGTH)
    		throw new InvalidShortUrlException();
        return shortUrlService.getFullUrl(path);    	
    }
}
