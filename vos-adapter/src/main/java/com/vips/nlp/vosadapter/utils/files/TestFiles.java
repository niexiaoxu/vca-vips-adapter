package com.vips.nlp.vosadapter.utils.files;

import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

@Log4j2
public class TestFiles {

    public static final LinkedHashSet<String> sourceSet = new LinkedHashSet<>();
    public static final LinkedHashSet<String> targetSet = new LinkedHashSet<>();

    public static void main(String[] args) {
//        readAndFormatWrite("/test/sjp2.csv", "/test/sjpt_2.csv");
        readAndWrite("/test/v1/source.csv", "/test/v1/target.csv", "/test/v1/diff.csv");
    }

    /**
     * 按行读文件，并格式化输出文件（"\"" + content + "\"\r\n"）
     *
     * @param readFilePath  读取文件路径
     * @param writeFilePath 写入文件路径
     */
    private static void readAndFormatWrite(String readFilePath, String writeFilePath) {
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
     * 提取源.CSV文件与目标.CSV文件交集行数，并写入新的.CVS文件
     *
     * @param sourceFilePath 源文件
     * @param targetFilePath 目标文件
     * @param diffFilePath   差异文件
     */
    public static void readAndWrite(String sourceFilePath, String targetFilePath, String diffFilePath) {
        long start = System.currentTimeMillis();
        String encoding = "UTF-8";
        int containsCount = 0;
        //write
        File writeFile = new File(diffFilePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(writeFile);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, encoding);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
            getLinkedHashSet(sourceSet, sourceFilePath, encoding);
            getLinkedHashSet(targetSet, targetFilePath, encoding);
            int scount = 0;
            for (String smid : sourceSet) {
                if (scount != 0) {
                    int tcount = 0;
                    for (String tmid : targetSet) {
                        if (tcount != 0) {
                            if (containsCount % 200 == 0) {
                                bufferedWriter.flush();
                            }
                            if (tmid.contains(smid)) {
                                log.info("相同： mid：{}, 源行数：{}, 目标行数：{}", new Object[]{smid, scount, tcount});
                                bufferedWriter.write(tmid);
                                bufferedWriter.newLine();
                                containsCount++;
                                break;
                            } else {
                                log.warn("差异： mid：{}, 源行数：{}, 目标行数：{}, 目标内容：{}", new Object[]{smid, scount, tcount, tmid});
                            }
                        }
                        tcount++;
                    }
                }
                scount++;
            }
            long end = System.currentTimeMillis();
            log.info("执行完毕，耗时：{}, 相同总数：{}", new Object[]{(end - start), containsCount});
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读写失败，错误信息：{}", e.getMessage());
        }
    }

    /**
     * 查看文件并检验可读性
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
     * 指定文件内容转linkedHashSet
     *
     * @param filePath 文件路径
     * @return
     */
    public static LinkedHashSet<String> getLinkedHashSet(LinkedHashSet<String> linkedHashSet, String filePath, String encoding) {
        File file = new File(filePath);
        if (!checkReadFile(file)) {
            return null;
        }
        if (linkedHashSet == null) {
            linkedHashSet = new LinkedHashSet<>();
        }
        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, encoding);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String lineContent = null;
            while ((lineContent = bufferedReader.readLine()) != null) {
                linkedHashSet.add(lineContent);
            }
        } catch (Exception e) {
            log.error("读取文件转LinkedHashSet异常，错误信息：{}", e.getMessage());
        }
        return linkedHashSet;
    }

    /**
     * 集合数据写文件
     *
     * @param filePath 文件路径
     * @param sets     数据集合
     * @param encoding 编码
     */
    public static void listToFile(String filePath, LinkedHashSet<String> sets, String encoding) {
        File writeFile = new File(filePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(writeFile);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, encoding);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
            for (String str : sets) {
                bufferedWriter.write(str);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            log.info("数据写入文件， 长度：{}， 路径：{}", new Object[]{sets.size(), filePath});
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据写入文件失败，错误信息：{}", e.getMessage());
        }
    }

    /**
     * 去重
     *
     * @param list
     */
    private static void removeDuplicate(List<String> list) {
        LinkedHashSet<String> set = new LinkedHashSet<String>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
    }
}
