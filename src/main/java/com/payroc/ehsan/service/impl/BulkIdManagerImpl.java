package com.payroc.ehsan.service.impl;

import com.payroc.ehsan.data.entity.GeneratedUrl;
import com.payroc.ehsan.data.entity.Parameter;
import com.payroc.ehsan.data.repository.GeneratedUrlRepository;
import com.payroc.ehsan.data.repository.ParameterRepository;
import com.payroc.ehsan.data.repository.ShortUrlRepository;
import com.payroc.ehsan.util.Constants;
import com.payroc.ehsan.service.BulkIdManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Prepare unused shorted url string for application usages
 * It helps to load pre prepared shorted urls from generated db to application ram
 * @author ehsan
 *
 */
@Service
public class BulkIdManagerImpl implements BulkIdManager {

    private final static Logger LOGGER = LogManager.getLogger(BulkIdManagerImpl.class);

    ShortUrlRepository shortUrlRepository;
    ParameterRepository parameterRepository;
    GeneratedUrlRepository generatedUrlRepository;
    public BulkIdManagerImpl(ShortUrlRepository shortUrlRepository, ParameterRepository parameterRepository, GeneratedUrlRepository generatedUrlRepository){
        this.shortUrlRepository=shortUrlRepository;
        this.parameterRepository=parameterRepository;
        this.generatedUrlRepository=generatedUrlRepository;
    }



    /**
     * It reserves a bulk of unused pre-generated shorted url strings from db to be loaded in ram
     * It has jpa locking to let multiple containers be able to load their own shorted url without conflict
     * @bulkSize size of reservation window
     */
    @Override
    @Transactional
    public List<GeneratedUrl> reserveUrlForConsumption(int bulkSize) throws Exception{

        Parameter curParam = parameterRepository.findByParamName(Constants.CURRENT_URL_INDEX).get(0);
        Parameter lastParam = parameterRepository.findByParamName(Constants.LAST_URL_INDEX).get(0);
        LOGGER.debug("curParam:{}, lastParam:{} Before generation", curParam.getLvalue(), lastParam.getLvalue());
        if(curParam.getLvalue()+bulkSize>lastParam.getLvalue()) {
            LOGGER.error("not enough url to dedicate consumed: {}, all:{}",curParam.getLvalue(), lastParam.getLvalue());
            throw new RuntimeException("not enough url to dedicate, produce more short url");
        }
        List<GeneratedUrl> resultGenUrl = generatedUrlRepository.getBulkGeneratedUrl(curParam.getLvalue(), curParam.getLvalue()+bulkSize);
        for(GeneratedUrl generatedUrl:resultGenUrl){
            generatedUrl.setVersion(1L);
        }
        generatedUrlRepository.saveAll(resultGenUrl);
        curParam.setLvalue(curParam.getLvalue()+bulkSize);
        parameterRepository.save(curParam);
        LOGGER.debug("curParam:{}, lastParam:{} After generation", curParam.getLvalue(), lastParam.getLvalue());
        LOGGER.debug("{} short url generated successfully", resultGenUrl.size());
        return resultGenUrl;
    }
}
