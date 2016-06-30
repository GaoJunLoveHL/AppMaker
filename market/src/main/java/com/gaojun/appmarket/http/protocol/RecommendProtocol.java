package com.gaojun.appmarket.http.protocol;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/29.
 */
public class RecommendProtocol extends BaseProtocol<ArrayList<String>> {
    @Override
    public String getKey() {
        return "recommend";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<String> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                String keyWord = ja.getString(i);
                list.add(keyWord);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
