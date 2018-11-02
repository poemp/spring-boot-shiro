package org.poem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author poem
 */
public class StringCompressUtils {

    public static void main(String[] args) {
        String code = "Banner eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJwZXJmb3JtZXIiLCJVU0VSIjoie1wiY3JlZGVudGlhbHNTYWx0XCI6XCJhZG1pbjBcIixcImxvY2tlZFwiOmZhbHNlLFwibmFtZVwiOlwi566h55CG5ZGYXCIsXCJwYXNzd29yZFwiOlwiMTIzNDU2XCIsXCJzYWx0XCI6XCI4ZDc4ODY5ZjQ3MDk1MTMzMjk1OTU4MDQyNGQ0YmY0ZlwiLFwic3RhdGVcIjpcIjBcIixcInN5c1JvbGVzXCI6W3tcImF2YWlsYWJsZVwiOmZhbHNlLFwiZGVzY3JpcHRpb25cIjpcIueuoeeQhuWRmFwiLFwiaWRcIjoxLFwicm9sZVwiOlwiYWRtaW5cIixcInN5c1Blcm1pc3Npb25zXCI6W3tcImF2YWlsYWJsZVwiOlwiMFwiLFwiaWRcIjoxLFwibmFtZVwiOlwi55So5oi3566h55CGXCIsXCJwZXJtaXNzaW9uXCI6XCJ1c2VySW5mb1ZPOnZpZXdcIixcInJlc291cmNlVHlwZVwiOlwibWVudVwifSx7XCJhdmFpbGFibGVcIjpcIjBcIixcImlkXCI6MSxcIm5hbWVcIjpcIueUqOaIt-euoeeQhlwiLFwicGVybWlzc2lvblwiOlwidXNlckluZm9WTzp2aWV3XCIsXCJyZXNvdXJjZVR5cGVcIjpcIm1lbnVcIn0se1wiYXZhaWxhYmxlXCI6XCIwXCIsXCJpZFwiOjIsXCJuYW1lXCI6XCLnlKjmiLfmt7vliqBcIixcInBlcm1pc3Npb25cIjpcInVzZXJJbmZvVk86YWRkXCIsXCJyZXNvdXJjZVR5cGVcIjpcImJ1dHRvblwifV19XSxcInVzZXJOYW1lXCI6XCJhZG1pblwifSIsImV4cCI6MTU0MTE0MzY1OSwiaWF0IjoxNTQxMTQzNjU5fQ.yl78SiATvkiHqRY0aepbAVAWP_yl3ysC-PDB0Mto76s";
        byte[] bytes = compress(code);
        System.err.println(bytes.length + "   " + code.length());
        System.err.println(new String(bytes));
    }
    /**
     * 压缩
     * @param paramString
     * @return
     */
    public static final byte[] compress(String paramString) {
        if (paramString == null){
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        byte[] arrayOfByte;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
            zipOutputStream.putNextEntry(new ZipEntry("0"));
            zipOutputStream.write(paramString.getBytes());
            zipOutputStream.closeEntry();
            arrayOfByte = byteArrayOutputStream.toByteArray();
        } catch (IOException localIOException5) {
            arrayOfByte = null;
        } finally {
            if (zipOutputStream != null){
                try {
                    zipOutputStream.close();
                } catch (IOException localIOException6) {
                }
            }
            if (byteArrayOutputStream != null){
                try {
                    byteArrayOutputStream.close();
                } catch (IOException localIOException7) {
                }
            }
        }
        return arrayOfByte;
    }

    /**
     * 解压
     * @param paramArrayOfByte
     * @return
     */
    @SuppressWarnings("unused")
    public static final String decompress(byte[] paramArrayOfByte) {
        if (paramArrayOfByte == null)
            return null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ZipInputStream zipInputStream = null;
        String str;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
            zipInputStream = new ZipInputStream(byteArrayInputStream);
            ZipEntry localZipEntry = zipInputStream.getNextEntry();
            byte[] arrayOfByte = new byte[1024];
            int i = -1;
            while ((i = zipInputStream.read(arrayOfByte)) != -1){
                byteArrayOutputStream.write(arrayOfByte, 0, i);
            }
            str = byteArrayOutputStream.toString();
        } catch (IOException localIOException7) {
            str = null;
        } finally {
            if (zipInputStream != null){
                try {
                    zipInputStream.close();
                } catch (IOException localIOException8) {
                }
            }
            if (byteArrayInputStream != null){
                try {
                    byteArrayInputStream.close();
                } catch (IOException localIOException9) {
                }
            }
            if (byteArrayOutputStream != null){
                try {
                    byteArrayOutputStream.close();
                } catch (IOException localIOException10) {
                }
            }
        }
        return str;
    }

}
