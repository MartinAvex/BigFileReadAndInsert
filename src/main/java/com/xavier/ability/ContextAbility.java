package com.xavier.ability;

import com.xavier.business.context.StandardContext;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 应用上下文
 * @author: ex_wuzr11
 * @date: 2024/10/22 17:01
 */
@Component
public class ContextAbility {

    @Resource
    private MultipartAbility multipartAbility;

    /**
     * 初始化上下文
     * @param file
     * @return
     */
    public StandardContext init(MultipartFile file) {
        try {
            File covert = multipartAbility.covert(file);
            int availableProcessorSize = Runtime.getRuntime().availableProcessors() * 2;
            StandardContext context = StandardContext.builder()
                    .availableProcessorSize(availableProcessorSize)
                    .eachFileSize(covert.length() / availableProcessorSize)
                    .randomAccessFile(new RandomAccessFile(covert, "r"))
                    .fileLength(covert.length())
                    .startEndPairSet(new HashSet<>())
                    .build();
            multipartAbility.calculateStartEnd(context);
            return context;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public ThreadPoolExecutor threadPoolExecutor(StandardContext context) {
        return new ThreadPoolExecutor(
                context.getAvailableProcessorSize(),
                context.getAvailableProcessorSize(),
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(context.getStartEndPairSet().size()),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }

}
