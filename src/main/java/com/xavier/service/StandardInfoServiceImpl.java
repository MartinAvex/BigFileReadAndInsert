package com.xavier.service;

import com.google.common.collect.Lists;
import com.xavier.ability.ContextAbility;
import com.xavier.ability.SliceFileAbility;
import com.xavier.business.SliceFile;
import com.xavier.business.StartEndPair;
import com.xavier.business.context.StandardContext;
import com.xavier.domian.StandardInfo;
import com.xavier.repository.StandardInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 逻辑实现
 * @author: ex_wuzr11
 * @date: 2024/10/22 16:25
 */
@Service
public class StandardInfoServiceImpl implements StandardInfoService {

    @Resource
    private StandardInfoRepository repository;
    @Resource
    private ContextAbility contextAbility;
    @Resource
    private SliceFileAbility sliceFileAbility;
    @Resource
    private PlatformTransactionManager dataSourceTransactionManager;
    @Resource
    private TransactionDefinition transactionDefinition;

    @Override
    public void analysisFile(MultipartFile file) {
        StandardContext standardContext = contextAbility.init(file);
        ThreadPoolExecutor threadPoolExecutor = contextAbility.threadPoolExecutor(standardContext);
        CountDownLatch countDownLatch = new CountDownLatch(standardContext.getStartEndPairSet().size());
        for (StartEndPair startEndPair : standardContext.getStartEndPairSet()) {
            threadPoolExecutor.execute(() -> {
                this.analysisPair(startEndPair, standardContext);
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void analysisPair(StartEndPair startEndPair, StandardContext standardContext) {
        SliceFile sliceFile = SliceFile.build(startEndPair);
        List<StandardInfo> dataList = sliceFileAbility.getDataList(standardContext, sliceFile);
        List<List<StandardInfo>> partitionList = Lists.partition(dataList, 2000);
        for (List<StandardInfo> partition : partitionList) {
            this.insertWithTransaction(partition);
        }
    }

    private void insertWithTransaction(List<StandardInfo> list) {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            repository.saveAllAndFlush(list);
            dataSourceTransactionManager.commit(transactionStatus);
        } catch (TransactionException e) {
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transactionStatus);
        }
    }

}
