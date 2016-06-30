package com.gaojun.appmarket.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.http.protocol.RecommendProtocol;
import com.gaojun.appmarket.ui.view.LoadingPage;
import com.gaojun.appmarket.ui.view.fly.ShakeListener;
import com.gaojun.appmarket.ui.view.fly.StellarMap;
import com.gaojun.appmarket.utils.UIUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * 推荐
 * Created by Administrator on 2016/6/27.
 */
public class RecommendFragment extends BaseFragment {
    private ArrayList<String> data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public View onCreateSuccessView() {

        final StellarMap stellar = new StellarMap(UIUtils.getContext());
        stellar.setAdapter(new RecommendAdapter());

        stellar.setRegularity(6, 9);
        int padding = UIUtils.dip2px(10);
        stellar.setInnerPadding(padding, padding, padding, padding);

        stellar.setGroup(0, true);
        ShakeListener shakeListener = new ShakeListener(UIUtils.getContext());
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                stellar.zoomIn();
            }
        });

        return stellar;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        RecommendProtocol protocol = new RecommendProtocol();
        data = protocol.getData(0);

        return check(data);
    }

    class RecommendAdapter implements StellarMap.Adapter {

        @Override
        public int getGroupCount() {
            return 3;
        }

        @Override
        public int getCount(int group) {
            int count = data.size() / getGroupCount();
            if (group == getGroupCount() - 1) {
                count += data.size() % getGroupCount();
            }
            return count;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            position += (group * getCount(group - 1));
            final String keyWord = data.get(position);
            TextView view = new TextView(UIUtils.getContext());
            view.setText(keyWord);

            Random random = new Random();

            int size = 12 + random.nextInt(10);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);

            int r = 30 + random.nextInt(200);
            int g = random.nextInt(230);
            int b = random.nextInt(230);
            view.setTextColor(Color.rgb(r,g,b));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(),keyWord,Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }

        //返回下一组的id
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (isZoomIn) {
                if (group > 0) {
                    return --group;
                } else {
                    return getGroupCount() - 1;
                }
            } else {
                if (group < getGroupCount() - 1) {
                    return ++group;
                } else {
                    return 0;
                }
            }
        }
    }
}
