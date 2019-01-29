package com.daveboy.marms4j.frame.util;

import com.google.gson.Gson;

public class GsonUtils {
    private static Gson gson;

    /**
     * 这里就不考虑线程安全了
     * @return
     */
    public static Gson getInstance(){
        if(gson==null){
            gson=new Gson();
        }
        return gson;
    }
}
