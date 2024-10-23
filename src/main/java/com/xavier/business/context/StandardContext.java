package com.xavier.business.context;

import com.xavier.business.StartEndPair;
import lombok.Builder;
import lombok.Data;

import java.io.RandomAccessFile;
import java.util.Set;

/**
 * @description: 应用上下文
 * @author: ex_wuzr11
 * @date: 2024/10/22 16:44
 */
@Data
@Builder
public class StandardContext {

    /**
     * 最大线程数
     */
    private Integer availableProcessorSize;

    /**
     * 单个文件长度
     */
    private Long eachFileSize;

    /**
     * 源文件长度
     */
    private Long fileLength;

    /**
     * 文件解析类
     */
    private RandomAccessFile randomAccessFile;

    /**
     * 分片Set
     */
    private Set<StartEndPair> startEndPairSet;

}
