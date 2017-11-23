package org.fsy.core;//package org.fsy.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 正则表达式基础表
 * 常用正则表达式符号含义
 * 1./d 数字
 * 2./l 小写字母
 * 3./u 大写字母
 *
 *
 *      有穷自动机的图形表示方法
 *              0-9       a-z      A-Z
 *  /d           1         0        0
 *  /l           0         1        0
 *  /u           0         0        1
 *
 * Created by fushiyong on 2017/11/23.
 */
public class Regex {

    private static String [] input = {"0","1","2","3","4","5","6","7","8","9"
                        ,"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"
                        ,"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    //需要预读 或者 再读的字符
    private String[] readMoreChar = {"/"};


    int [][] autoMachineArr = {
            { /** 0-9 **/   1,1,1,1,1,1,1,1,1,1
              /** a-z **/   ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0     //状态数字行
              /** A-Z **/   ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
            }
            ,{
                /** 0-9 **/   0,0,0,0,0,0,0,0,0,0
                /** a-z **/   ,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
                /** A-Z **/   ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0   //状态小写字母行
    }
            ,{
            /** 0-9 **/   0,0,0,0,0,0,0,0,0,0
            /** a-z **/   ,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
            /** A-Z **/   ,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1   //状态大写字母行
    }
    };



    private String inputStr ;

    public List<String> tokenList = new ArrayList();

    public Regex(String regex , String inputStr){

        tokenParser(regex);

        this.inputStr = inputStr;
    }

    //可以优化 map来接
    private int findTokenPos(String token) {
        for(int i = 0 ;i<input.length ; i++){
            String object = input[i];
            if(object.equals(token)){
                return i;
            }
        }
        return -1;
    }

    /**
     * 将输入表达式解析成单词列表
     * @param regex
     * @return
     */
    private void tokenParser(String regex) {

        char  chars [] = regex.toCharArray();

        String token = "";
        for(int i = 0 ; i< chars.length; i++){

            char currentChar = chars[i];

            if(Arrays.asList(readMoreChar).contains(currentChar+"")){
                token+= currentChar ;
                continue;
            }else{
                this.tokenList.add(token+currentChar);
                token = "";
                continue;
            }

        }
    }

    /**
     * 判断输入字符串是否匹配正则表达式
     * @return
     */
    public  boolean match(){

        //遍历正则序列 ,  所有条件都要满足
        int arrLine = -1;


        int calCount = 0; //计算次数
        for(int i = 0 ; i<tokenList.size() ; i++){

            String token = tokenList.get(i);

            if("/d".equals(token)){
                arrLine = 0;

            }else if("/l".equals(token)){

                arrLine = 1;
            }else if("/u".equals(token)){
                arrLine = 2;
            }else{

            }
            for(int j = i; j<this.inputStr.toCharArray().length ; j++){
                char cha = this.inputStr.toCharArray()[j];
                int index = findTokenPos(cha+"");
                if(index != -1){
                    if(autoMachineArr[arrLine][index] == 1 ){
                        //继续执行下一个词法分析
                        break;
                    }else{
                        //直接返回匹配失败
                        return false;

                    }
                }else {
                    throw new IllegalArgumentException(" 词法解析出错 !!!请核查!!!");
                }

            }
        }


        return true;
    }


    public static void main(String[] args) {
        String regex = "/d/l/u";


        //有穷状态机 解决
        //五元组
        //1.状态组 真 1 或者 假  0
        //2.
        //3.当前输入  0-9a-zA-Z
        //4.

        System.out.println(new Regex("/d/l/u/d/d/d/d/d/d","1aA123449").match());

    }

}

