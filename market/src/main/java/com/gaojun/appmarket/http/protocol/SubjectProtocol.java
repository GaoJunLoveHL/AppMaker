package com.gaojun.appmarket.http.protocol;

import com.gaojun.appmarket.domain.SubjectInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 专题
 * Created by Administrator on 2016/6/29.
 */
public class SubjectProtocol extends BaseProtocol<ArrayList<SubjectInfo>> {
    @Override
    public String getKey() {
        return "subject";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<SubjectInfo> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<SubjectInfo> infos = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                SubjectInfo info = new SubjectInfo();
                info.des = jo.getString("des");
                info.url = jo.getString("url");
                infos.add(info);
            }
            return infos;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
