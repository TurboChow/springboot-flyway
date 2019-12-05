package com.springboot.demo.flyway.crc32;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

public class CRC32Test {

    public static void main(String[] args) {
        try {
            System.out.println(getCRC32("/Users/zhoutao/IdeaProjects/springboot-flyway/src/main/resources/db/migration/V1__create_table_2018.10.12.001.sql"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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



