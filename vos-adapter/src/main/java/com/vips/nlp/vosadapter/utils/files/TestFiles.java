package com.vips.nlp.vosadapter.utils.files;

import lombok.extern.log4j.Log4j2;

import java.io.*;

@Log4j2
public class TestFiles {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
//        readAndWrite("/test/sjp2.csv", "/test/sjpt_2.csv");
        readAndWrite("/test/sjpt_2.csv", "/test/mid_all_parse.csv","/test/diff.csv");
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
        if(!checkReadFile(readFile)){
            return;
        }
        File writeFile = new File(writeFilePath);
        try(FileInputStream fileInputStream = new FileInputStream(readFile);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, encoding);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                FileOutputStream fileOutputStream = new FileOutputStream(writeFile);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, encoding);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)){
            String lineTxt = null;
            int count = 0;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                log.info("当前行数：{}, 内容：{}", new Object[]{count, lineTxt});
                lineTxt = "\"" + lineTxt + "\"\r\n";
                bufferedWriter.write(lineTxt);
                count++;
            }
        }catch (Exception e){
            log.error("读写异常，错误信息：{}", e.getMessage());
        }
    }

    /**
     *  查看文件可读性
     * @param file
     * @return
     */
    private static boolean checkReadFile(File file){
        boolean result = true;
        if(!file.isFile() || !file.exists() || !file.canRead()){
            log.error("当前文件状态, isFile:{}, exists:{}, canRead:{}", new Object[]{file.isFile(), file.exists(), file.canRead()});
            result = false;
        }
        log.info("当前文件大小：{}", file.length());
        return result;
    }


    /**
     * 比较并记录源文件与目标文件的差异部分
     * @param sourceFilePath 源文件
     * @param targetFilePath 目标文件
     * @param diffFilePath 差异文件
     */
    public static void readAndWrite(String sourceFilePath, String targetFilePath, String diffFilePath) {
        String encoding = "UTF-8";
        File sourceFile = new File(sourceFilePath);
        File targetFile = new File(targetFilePath);
        if(!checkReadFile(sourceFile) || !checkReadFile(targetFile)){
            return;
        }
        File writeFile = new File(diffFilePath);
        try(FileInputStream sourcefileInputStream = new FileInputStream(sourceFile);
            InputStreamReader sourceinputStreamReader = new InputStreamReader(sourcefileInputStream, encoding);
            BufferedReader sourcebufferedReader = new BufferedReader(sourceinputStreamReader);
            FileInputStream targetFilefileInputStream = new FileInputStream(targetFile);
            InputStreamReader targetFileinputStreamReader = new InputStreamReader(targetFilefileInputStream, encoding);
            BufferedReader targetFilebufferedReader = new BufferedReader(targetFileinputStreamReader);
            FileOutputStream fileOutputStream = new FileOutputStream(writeFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, encoding);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)){
            String slineTxt = null;
            int scount = 0;
            String tlinTxt = null;
            int tcount = 0;
            while ((slineTxt = sourcebufferedReader.readLine()) != null) {
                while ((tlinTxt = targetFilebufferedReader.readLine()) != null) {
                    if(scount==1 || (scount>0 && tcount==0)){
                        break;
                    }
                    if(slineTxt.equals(tlinTxt)){
                        log.error("当前相同：源数据：{} 源行数：{}, 目标数据：{} 目标行数：{}", new Object[]{slineTxt, scount, tlinTxt, tcount});
                        slineTxt = "\"" + slineTxt + "\"\r\n";
                        bufferedWriter.write(slineTxt);
                        bufferedWriter.flush();
                    }else{
                        log.error("当前差异：源数据：{} 源行数：{}, 目标数据：{} 目标行数：{}", new Object[]{slineTxt, scount, tlinTxt, tcount});
                    }
                    tcount++;
                }
                scount++;
            }
        }catch (Exception e){
            log.error("读写异常，错误信息：{}", e.getMessage());
        }
        log.info("finsh...");
    }
}
