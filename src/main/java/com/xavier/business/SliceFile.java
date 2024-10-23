package com.xavier.business;

import lombok.Builder;
import lombok.Data;

/**
 * @description: 文件片
 * @author: ex_wuzr11
 * @date: 2024/10/21 17:31
 */
@Data
@Builder
public class SliceFile {

    /**
     * 开始位置
     */
    private Long start;

    /**
     * 每次读取长度
     */
    private Long sliceSize;

    /**
     * 每次读取缓存大小
     */
    private byte[] readBuffer;

    public static SliceFile build(StartEndPair startEndPair) {
        return SliceFile.builder()
                .start(startEndPair.start)
                .sliceSize(startEndPair.end - startEndPair.start + 1)
                .readBuffer(new byte[Math.toIntExact((startEndPair.end - startEndPair.start + 1))])
                .build();
    }

}
