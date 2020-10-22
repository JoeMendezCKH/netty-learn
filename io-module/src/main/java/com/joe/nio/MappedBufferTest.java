package com.joe.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * MappedBuffer 可以让文件直接在内存(堆外内存)修改, OS 不用拷贝一次
 *
 * @author ckh
 * @create 10/22/20 3:24 PM
 */
public class MappedBufferTest {

    public static void main(String[] args) throws IOException {

        RandomAccessFile randomAccessFile = new RandomAccessFile("io-module/src/main/resources/01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        /**
         * @param  mode    使用的读写模式
         *         One of the constants READ_ONLY,READ_WRITE or PRIVATE
         *         defined in the MapMode class, according to whether
         *         the file is to be mapped read-only, read/write, or
         *         privately (copy-on-write), respectively
         *
         * @param  position 直接修改的起始位置
         *         The position within the file at which the mapped region
         *         is to start; must be non-negative
         *
         * @param  size     映射到内存的大小, 不是索引位置, 即文件的多少个字节
         *         The size of the region to be mapped; must be non-negative and
         *         no greater than Integer.MAX_VALUE
         *
         * MappedByteBuffer actual type is DirectByteBuffer
         */
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');

        // mappedByteBuffer.put(5, (byte) '9'); IndexOutOfBoundsException

        randomAccessFile.close();

        System.out.println("success");
    }
}
