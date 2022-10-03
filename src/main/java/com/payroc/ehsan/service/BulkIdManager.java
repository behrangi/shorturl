package com.payroc.ehsan.service;

import com.payroc.ehsan.data.entity.GeneratedUrl;

import java.util.List;

public interface BulkIdManager {
//    List<GeneratedUrl> generateId();
    List<GeneratedUrl> reserveUrlForConsumption(int bulkSize) throws Exception;
}
