package com.xavier.ability;

import com.alibaba.fastjson2.JSON;
import com.xavier.business.SliceFile;
import com.xavier.business.context.StandardContext;
import com.xavier.domian.StandardInfo;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @description: some desc
 * @author: ex_wuzr11
 * @date: 2024/10/22 17:14
 */
@Component
public class SliceFileAbility {

    public List<StandardInfo> getDataList(StandardContext context, SliceFile sliceFile) {
        try {
            MappedByteBuffer mapBuffer = context.getRandomAccessFile()
                    .getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, sliceFile.getStart(), sliceFile.getSliceSize());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            mapBuffer.get(sliceFile.getReadBuffer(), 0, Math.toIntExact(sliceFile.getSliceSize()));
            bos.write(sliceFile.getReadBuffer());

            String handlerString = null;
            byte[] readByte = bos.toByteArray();
            if (readByte.length > 0) {
                handlerString = preHandler(new String(readByte, StandardCharsets.UTF_8));
            }
            return JSON.parseArray(handlerString, StandardInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private String preHandler(String subFileString) {
        while (subFileString.endsWith(",") || subFileString.endsWith("\r") || subFileString.endsWith("\n")) {
            subFileString = subFileString.substring(0, subFileString.length() - 1);
        }
        subFileString = subFileString.replaceAll("\\[", "").replaceAll("\\]", "");
        subFileString = "[".concat(subFileString).concat("\r\n]");
        return subFileString;
    }

}
