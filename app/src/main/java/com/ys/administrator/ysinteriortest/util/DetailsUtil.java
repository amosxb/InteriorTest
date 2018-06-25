package com.ys.administrator.ysinteriortest.util;

/**
 * Created by amos on 2017/8/10.
 */

public class DetailsUtil {

    public static DetailsUtil getInstance(){
        return DetailsUtilInstance.sDetailsUtil;
    }

    public static class DetailsUtilInstance{
        private static DetailsUtil sDetailsUtil = new DetailsUtil();
    }
}
