package com.gaojun.appmarket.http.protocol;

import com.gaojun.appmarket.domain.CategroyInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 分类模块网络请求
 * Created by Administrator on 2016/6/29.
 */
public class CategoryProtocol extends BaseProtocol<ArrayList<CategroyInfo>>{
    @Override
    public String getKey() {
        return "category";
    }

    @Override
    public String getParams() {
        return "";
    }

    @Override
    public ArrayList<CategroyInfo> parseData(String result) {
        try {
            JSONArray ja = new JSONArray(result);
            ArrayList<CategroyInfo> list = new ArrayList<>();

            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                if (jo.has("title")){
                    CategroyInfo mCategroyInfo = new CategroyInfo();
                    mCategroyInfo.title = jo.getString("title");
                    mCategroyInfo.isTitle = true;
                    list.add(mCategroyInfo);
                }
                if (jo.has("infos")){
                    JSONArray ja1 = jo.getJSONArray("infos");
                    for (int j = 0; j < ja1.length(); j++) {
                        JSONObject jo1 = ja1.getJSONObject(j);
                        CategroyInfo info = new CategroyInfo();
                        info.name1 = jo1.getString("name1");
                        info.name2 = jo1.getString("name2");
                        info.name3 = jo1.getString("name3");
                        info.url1 = jo1.getString("url1");
                        info.url2 = jo1.getString("url2");
                        info.url3 = jo1.getString("url3");
                        info.isTitle = false;
                        list.add(info);
                    }
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
