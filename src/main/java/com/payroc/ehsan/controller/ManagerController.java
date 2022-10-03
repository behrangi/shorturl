package com.payroc.ehsan.controller;

import com.payroc.ehsan.service.impl.DbInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;


/**
 * 
 * @author ehsan
 * TODO
 * This /admin path must be protected by spring security in the future or being transfered to another safe app
 */
@RestController
@RequestMapping(value="/admin")
public class ManagerController {
    final static Logger LOGGER = LogManager.getLogger(ManagerController.class);
    DbInitializer dbInitializer;
     public ManagerController(DbInitializer dbInitializer){
         this.dbInitializer=dbInitializer;
     }

     /**
      * Just initialise Parameter table
      * 
      */
    @Transactional
    @RequestMapping(value = "/init", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8", method = RequestMethod.GET)
    public String init(){
         try {
             dbInitializer.init();
             return "done!";
         }catch (Exception exception){
             LOGGER.error(exception);
             return "problem occured!";
         }
    }

    /**
     * Generate lots of short keys offline for being used by application in deployment 
     * Supposedly, it can generate all the valid keys and save them somewhere before main application runs
     */
    @Transactional
    @RequestMapping(value = "/genkey", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8", method = RequestMethod.GET)
    public String genKey(){
         dbInitializer.generate();
         return "done";
    }


}
