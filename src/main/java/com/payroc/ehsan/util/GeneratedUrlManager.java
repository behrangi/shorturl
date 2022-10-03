package com.payroc.ehsan.util;

import com.payroc.ehsan.data.entity.GeneratedUrl;
import com.payroc.ehsan.service.BulkIdManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * I do not like this service but I need time to improve it
 * It is loading pre-generated shorted urls for assignment to long urls by application and bringing them to ram
 * It is using a blocking queue to sync consumers and producers
 * Consumers are ui services thread and producer is urlProducer service 
 * @author ehsan
 *
 */
@Service
public class GeneratedUrlManager{
    private final static Logger LOGGER = LogManager.getLogger(GeneratedUrlManager.class);


    final static private BlockingQueue<GeneratedUrl> GENERATED_URL_CACHE = new ArrayBlockingQueue<GeneratedUrl>(Constants.QUEUE_SIZE);
    BulkIdManager bulkIdManager;
    TaskExecutor taskExecutor;
    public GeneratedUrlManager(BulkIdManager bulkIdManager, TaskExecutor taskExecutor){
        this.bulkIdManager = bulkIdManager;
        this.taskExecutor=taskExecutor;
    }
    @PostConstruct
    public void init(){
        taskExecutor.execute(new UrlProducer());
    }

    public static GeneratedUrl getGeneratedUrlForConsumption(){
        try {
            return GENERATED_URL_CACHE.take();
        }catch (Exception ex){
            return null;
        }
    }
    private class UrlProducer implements Runnable{
        @Override
        public void run() {
            while(true) {
        	 try {
                List<GeneratedUrl> results = bulkIdManager.reserveUrlForConsumption(Constants.BULK_SIZE);
                LOGGER.debug("Queue ready url BEFORE generation:{} remaining capacity:{}", GENERATED_URL_CACHE.size(), GENERATED_URL_CACHE.remainingCapacity());
                for (GeneratedUrl generatedUrl : results) {
                    GENERATED_URL_CACHE.put(generatedUrl);
                }
                LOGGER.debug("Queue ready url AFTER generation:{} remaining capacity:{}", GENERATED_URL_CACHE.size(), GENERATED_URL_CACHE.remainingCapacity());
        	 }catch (Exception exception){
                 LOGGER.debug("problem in url producer thread", exception);
                     try {
                     Thread.sleep(10000);
                     }catch(Exception ex) {}
                 }
              }
         }            
    }    

}



