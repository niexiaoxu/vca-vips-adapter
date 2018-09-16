package com.vips.nlp.vosadapter.utils.files;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Log4j2
public class TestFiles {
    public static void main(String[] args) throws InterruptedException {
//        readAndWrite("/test/sjp2.csv", "/test/sjpt_2.csv");
//        Thread.sleep(15000);
        readAndWrite("/test/sjpt_2.csv", "/test/mid_all_parse.csv", "/test/diff.csv");
    }

    /**
     * 读写文件
     *
     * @param readFilePath  读取文件路径
     * @param writeFilePath 写入文件路径
     */
    public static void readAndWrite(String readFilePath, String writeFilePath) {
        String encoding = "UTF-8";
        File readFile = new File(readFilePath);
        if (!checkReadFile(readFile)) {
            return;
        }
        File writeFile = new File(writeFilePath);
        try (FileInputStream fileInputStream = new FileInputStream(readFile);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, encoding);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
             FileOutputStream fileOutputStream = new FileOutputStream(writeFile);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, encoding);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
            String lineTxt = null;
            int count = 0;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                log.info("当前行数：{}, 内容：{}", new Object[]{count, lineTxt});
                lineTxt = "\"" + lineTxt + "\"\r\n";
                bufferedWriter.write(lineTxt);
                count++;
            }
        } catch (Exception e) {
            log.error("读写异常，错误信息：{}", e.getMessage());
        }
    }

    /**
     * 查看文件可读性
     *
     * @param file
     * @return
     */
    private static boolean checkReadFile(File file) {
        boolean result = true;
        if (!file.isFile() || !file.exists() || !file.canRead()) {
            log.error("当前文件状态, isFile:{}, exists:{}, canRead:{}", new Object[]{file.isFile(), file.exists(), file.canRead()});
            result = false;
        }
        log.info("当前文件大小：{}", file.length());
        return result;
    }


    /**
     * 比较并记录源文件与目标文件的差异部分
     *
     * @param sourceFilePath 源文件
     * @param targetFilePath 目标文件
     * @param diffFilePath   差异文件
     */
    public static void readAndWrite(String sourceFilePath, String targetFilePath, String diffFilePath) {
        long start = System.currentTimeMillis();
        String encoding = "UTF-8";
        File sourceFile = new File(sourceFilePath);
        File targetFile = new File(targetFilePath);
        if (!checkReadFile(sourceFile) || !checkReadFile(targetFile)) {
            return;
        }
        //init read
        FileInputStream sourcefileInputStream = null;
        InputStreamReader sourceinputStreamReader = null;
        BufferedReader sourcebufferedReader = null;
        //init write
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            //source
            sourcefileInputStream = new FileInputStream(sourceFile);
            sourceinputStreamReader = new InputStreamReader(sourcefileInputStream, encoding);
            sourcebufferedReader = new BufferedReader(sourceinputStreamReader);
            String slineTxt = null;
            LinkedHashSet<String> sourceSet = new LinkedHashSet<>();
            while ((slineTxt = sourcebufferedReader.readLine()) != null) {
                sourceSet.add(slineTxt);
            }
            //target
            LinkedHashSet<String> targetSet = new LinkedHashSet<>();
            sourcefileInputStream = new FileInputStream(targetFile);
            sourceinputStreamReader = new InputStreamReader(sourcefileInputStream, encoding);
            sourcebufferedReader = new BufferedReader(sourceinputStreamReader);
            while ((slineTxt = sourcebufferedReader.readLine()) != null) {
                targetSet.add(slineTxt);
            }
            //write
            File writeFile = new File(diffFilePath);
            fileOutputStream = new FileOutputStream(writeFile);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, encoding);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            int scount = 0;
            for (String smid : sourceSet) {
                scount++;
                int tcount = 0;
                for (String tmid : targetSet) {
                    tcount++;
                    if (smid.equals(tmid)) {
                        log.info("相同： mid：{}, 源行数：{}, 目标行数：{}", new Object[]{smid, scount, tcount});
                        bufferedWriter.write(smid + "\r\n");
                        bufferedWriter.flush();
                        continue;
                    } else {
                        log.warn("差异： mid：{}, 源行数：{}, 目标行数：{}", new Object[]{smid, scount, tcount});
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读写失败，错误信息：{}", e.getMessage());
        } finally {
            try {
                bufferedWriter.close();
                outputStreamWriter.close();
                fileOutputStream.close();
                sourcebufferedReader.close();
                sourceinputStreamReader.close();
                sourcefileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("关闭流失败，错误信息：{}", e.getMessage());
            }
        }
        long end = System.currentTimeMillis();
        log.info("执行完毕，耗时：{}", end - start);
    }

    /**
     * 去重
     * @param list
     */
    private static void removeDuplicate(List<String> list) {
        LinkedHashSet<String> set = new LinkedHashSet<String>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
    }
}
