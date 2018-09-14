package com.vips.nlp.vosadapter.utils.files;


import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * CVS TOOLS
 */
@Log4j2
public class CsvFileUtil {

    public static List<Map<String, String>> readFile(File file) {
        List<Map<String, String>> allData = new ArrayList();
        CSVParser csvFileParser = null;
        try {
            FileReader fileReader = new FileReader(file);
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
            // 初始化 CSVParser object
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            Map<String, Integer> headerMap = csvFileParser.getHeaderMap();
            // CSV文件records
            List<CSVRecord> csvRecords = csvFileParser.getRecords();
            for (CSVRecord record : csvRecords) {
                int size = record.size();
                Map<String, String> map = new LinkedHashMap();
                boolean isSkip = false;
                if (isSkip) {
                    log.info("跳过...");
                    break;
                }
                for (int i = 0; i < size; i++) {
                    if (isSkip) {
                        break;
                    }
                    for (Map.Entry<String, Integer> entry : headerMap.entrySet()) {
                        if (entry.getValue() == i) {
                            String value = record.get(i);
                            if (i == 0 ) {
                                isSkip = true;
                            } else {
                                map.put(entry.getKey(), record.get(i));
                            }
                            break;
                        }
                    }
                }
                if (!isSkip) {
                    allData.add(map);
                }
            }
        } catch (Exception e) {
            log.info("读取CVS文件失败，错误信息：{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(csvFileParser);
        }
        return allData;
    }

    public static Map<String, Integer> getHeaderMap(File file) {
        CSVParser csvFileParser = null;
        Map<String, Integer> headerMap = null;
        try {
            FileReader fileReader = new FileReader(file);
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
            // 初始化 CSVParser object

            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            headerMap = csvFileParser.getHeaderMap();
        } catch (Exception e) {
            log.error("获取CVS head失败，错误信息:{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(csvFileParser);
        }
        return headerMap;
    }

    public static void main(String[] args) {
        String fileUrl = "E:\\test\\a_dq.csv";
        List<Map<String, String>> maps = readFile(new File(fileUrl));
        log.info("当前：{}，长度：{}", new Object[]{fileUrl, maps.size()});
    }

}
