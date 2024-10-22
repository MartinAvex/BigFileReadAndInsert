package com.xavier.ability;

import com.xavier.business.StartEndPair;
import com.xavier.business.context.StandardContext;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @description: 文件转换
 * @author: ex_wuzr11
 * @date: 2024/10/22 16:39
 */
@Component
public class MultipartAbility {

    public File covert(MultipartFile file) {
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) file;
        DiskFileItem diskFileItem = (DiskFileItem) commonsMultipartFile.getFileItem();
        return diskFileItem.getStoreLocation();
    }


    public void calculateStartEnd(StandardContext context) {
        calculateStartEnd(0, context);
    }


    /**
     * 计算每一个文件分片的起始位置
     * @param start 开始位置
     * @param context 上下文
     */
    private static void calculateStartEnd(long start, StandardContext context) {
        long maxIndex = context.getFileLength() - 1;
        if (context.getEachFileSize() > maxIndex) {
            return;
        }
        StartEndPair startEndPair = new StartEndPair();
        startEndPair.start = start;
        long endPosition = start + context.getEachFileSize() - 1;
        if (endPosition >= maxIndex) {
            startEndPair.end = maxIndex;
            context.getStartEndPairSet().add(startEndPair);
            return;
        }
        try {
            RandomAccessFile randomAccessFile = context.getRandomAccessFile();
            randomAccessFile.seek(endPosition);
            byte tmp = (byte) randomAccessFile.read();
            while (tmp != '}') {
                endPosition++;
                if (endPosition >= maxIndex) {
                    endPosition = maxIndex;
                    break;
                }
                randomAccessFile.seek(endPosition);
                tmp = (byte) randomAccessFile.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        startEndPair.end = endPosition + 2;
        context.getStartEndPairSet().add(startEndPair);
        calculateStartEnd(endPosition + 2, context);
    }

}
