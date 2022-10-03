package com.payroc.ehsan.service.impl;

import com.payroc.ehsan.data.entity.GeneratedUrl;
import com.payroc.ehsan.data.entity.Parameter;
import com.payroc.ehsan.data.repository.GeneratedUrlRepository;
import com.payroc.ehsan.data.repository.ParameterRepository;
import com.payroc.ehsan.util.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbInitializer {

    private final static Logger LOGGER = LogManager.getLogger(DbInitializer.class);

    ParameterRepository parameterRepository;
    GeneratedUrlRepository generatedUrlRepository;

    public DbInitializer(ParameterRepository parameterRepository, GeneratedUrlRepository generatedUrlRepository){
        this.parameterRepository = parameterRepository;
        this.generatedUrlRepository = generatedUrlRepository;
    }

    public synchronized void init(){

        List<Parameter> params = parameterRepository.findAll();
        if(params.size()!=0)
            parameterRepository.deleteAll();

        Parameter curUrlCounter = new Parameter(Constants.CURRENT_URL_INDEX, 0L);
        Parameter lastUrlCounter = new Parameter(Constants.LAST_URL_INDEX, 0L);
        parameterRepository.save(curUrlCounter);
        parameterRepository.save(lastUrlCounter);
    }

/**
 * A simple service to build lots of unused short key strings for the main application
 * In reality we must generate whole the 7 AlphaNumeric characters and save them some where before app runs 
 */
    public synchronized void generate(){
        Long curUrlCounter, lastUrlCounter;
        Parameter lastUrlParam = null;
        try{
            curUrlCounter = parameterRepository.findByParamName(Constants.CURRENT_URL_INDEX).get(0).getLvalue();
        }
        catch (Exception ex){
            LOGGER.error("Can not read curUrlCounter, did you call init service?", ex);
            curUrlCounter = 0L;
        }
        try{
            lastUrlParam = parameterRepository.findByParamName(Constants.LAST_URL_INDEX).get(0);
            lastUrlCounter = lastUrlParam.getLvalue();
        }
        catch (Exception ex){
            LOGGER.error("Can not read lastUrlParam, did you call init service?", ex);
            lastUrlCounter = 0L;
        }
        while(lastUrlCounter-curUrlCounter<=Constants.MAX_BULK){
            try {
                GeneratedUrl genUrl = new GeneratedUrl(RandomStringUtils.randomAlphanumeric(Constants.SHORT_URL_LENGTH));
                generatedUrlRepository.save(genUrl);
                lastUrlCounter++;
            }catch (Exception ex){
                LOGGER.debug(ex);
            }
        }

        if(lastUrlParam!=null){
            lastUrlParam.setLvalue(lastUrlCounter);
            parameterRepository.save(lastUrlParam);
        }
    }

}
