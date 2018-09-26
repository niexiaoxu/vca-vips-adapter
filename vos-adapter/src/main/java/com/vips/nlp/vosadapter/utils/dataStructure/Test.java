package com.vips.nlp.vosadapter.utils.dataStructure;


import lombok.extern.log4j.Log4j2;

import java.util.*;

@Log4j2
public class  Test{

    public static final ArrayList<Integer> dataLists = new ArrayList<Integer>();

    /**
     * 初始化数据
     */
    public static void initData(int max) {
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<Integer>();
        Random random = new Random();
        int i = 0;
        while (i < max) {
            int data = random.nextInt(max);
            log.info("随机数：{}", data);
            boolean add = linkedHashSet.add(data);
            if(add){
                dataLists.add(data);
            }
            i++;
        }
    }

    /**
     * 初始化有序数据
     */
    public static void initSortData(int max) {
        for (int i = 0; i < max; i++) {
            dataLists.add(i*2);
        }

    }

    /**
     * binary search tree
     * @param key 查询内容
     * @param lists 指定数据
     * @param lo 起始索引
     * @param hi 截止索引
     * @return
     */
    public static int bstIndex(int key, ArrayList<Integer> lists, int lo, int hi) {

        int index = -1;
        if(lo > hi){
           return index;
        }
        int mid = lo + (( hi -lo) >>> 1);
        if ( key > lists.get(mid) ){
            index =  bstIndex(key, lists, mid + 1, hi);
        }else if ( key < lists.get(mid)){
            index =  bstIndex(key, lists, lo, mid -1);
        }else {
            index =  mid;
        }
        return index;
    }

    /**
     * 普通遍历
     * @param key 查询内容
     * @param lists 指定数据
     * @param lo 起始索引
     * @param hi 截止索引
     * @return
     */
    public static int searchIndex(int key, ArrayList<Integer> lists, int lo, int hi) {
        int index = -1;
        for (int i = 0; i < lists.size(); i++) {
            if(lists.get(i) == key){
                index = i;
                break;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        initSortData(10000000);
        log.info("当前set长度:{}", dataLists.size());
        long search_start = System.currentTimeMillis();
        //        性能测试，当前索引位置：9876452, 耗时：0ms
        int index = bstIndex(9876452, dataLists, 0, dataLists.size()-1);

//        int index = searchIndex(9876452, dataLists, 0, dataLists.size()-1);

        long end = System.currentTimeMillis();
        log.info("性能测试，当前索引位置：{}, 该索引位置值：{}, 总耗时：{}ms， 搜索耗时：{}", new Object[]{index, dataLists.get(index), end-start, end-search_start});

    }


}
