package leetocdetest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author virtual
 * @Date 2021/9/5 23:17
 * @Version 1.0
 */
class Solution {
    private static String[] letterMap = new String[]{
            "", // 0
            "", // 1
            "abc", // 2
            "def", // 3
            "ghi", // 4
            "jkl", // 5
            "mno", // 6
            "pqrs", // 7
            "tuv", // 8
            "wxyz", // 9
    };

    public static List<String> letterCombinations(String digits) {

        String path="";
        System.out.println(digits.toCharArray()[0]);
        return backtarck(0,digits.length(),digits.toCharArray(),new ArrayList<String>(),path);

    }

    public static List<String> backtarck(int k,int n,char[] digits,List<String> res,String path){
        if(k==n){
            res.add(path);
            return res;
        }

        String let=letterMap[k];

        for(int j=0;j<let.length();j++){
            path += let.charAt(j);
            backtarck(k+1,n,digits,res,path);
            path.substring(0,path.length()-1);
        }

        return res;



    }

    public static void main(String[] args) {
        letterCombinations("23");
    }
}