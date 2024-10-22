package com.xavier.business.context;

import com.xavier.business.StartEndPair;
import lombok.Builder;
import lombok.Data;

import java.io.RandomAccessFile;
import java.util.Set;

/**
 * @description: 上下文
 * @author: ex_wuzr11
 * @date: 2024/10/22 16:44
 */
@Data
@Builder
public class StandardContext {

    private Integer availableProcessorSize;
    private Long eachFileSize;
    private Long fileLength;
    private RandomAccessFile randomAccessFile;
    private Set<StartEndPair> startEndPairSet;

}
