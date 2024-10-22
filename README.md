# 两部分：

## 1、文件上传解析

    首先，根据上传的文件长度`covert.length()`和可用的线程数`availableProcessorSize`来计算平均每个线程需要处理的文件长度，以此来构建每一个分片对象`StartEndPair`。  
    然后，借助`RandomAccessFile`来解析文件获取数据集。

## 2、大数据量批量插入

    借助线程池对数据进行批处理
    配置文件加上：&rewriteBatchedStatements=true，防止批量插入不生效
  
