package com.springboot.demo.flyway.crc32;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class CRCTest {

    public static void main(String[] args) {
        try {
            System.out.println(getCRC32("/Users/zhoutao/IdeaProjects/springboot-flyway/src/main/resources/db/migration/V1__create_table_2018.10.12.001.sql"));
            System.out.println(checksumBufferedInputStream("/Users/zhoutao/IdeaProjects/springboot-flyway/src/main/resources/db/migration/V1__create_table_2018.10.12.001.sql"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 采用BufferedInputStream的方式加载文件
     */
    public static long checksumBufferedInputStream(String filepath) throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filepath));
        CRC32 crc = new CRC32();
        byte[] bytes = new byte[1024];
        int cnt;
        while ((cnt = inputStream.read(bytes)) != -1) {
            crc.update(bytes, 0, cnt);
        }
        inputStream.close();
        return crc.getValue();
    }

    /**
     * 使用CheckedInputStream计算CRC
     */
    public static Long getCRC32(String filepath) throws IOException {
        CRC32 crc32 = new CRC32();
        FileInputStream fileinputstream = new FileInputStream(new File(filepath));
        CheckedInputStream checkedinputstream = new CheckedInputStream(fileinputstream, crc32);
        while (checkedinputstream.read() != -1) {
        }
        checkedinputstream.close();
        return crc32.getValue();
    }
}



