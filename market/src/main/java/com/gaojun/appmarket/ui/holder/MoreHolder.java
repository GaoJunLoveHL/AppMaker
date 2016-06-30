package com.gaojun.appmarket.ui.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaojun.appmarket.R;
import com.gaojun.appmarket.utils.UIUtils;

/**
 * Created by Administrator on 2016/6/28.
 */
public class MoreHolder extends BaseHolder<Integer> {

    public static final int STATE_LOAD_MORE = 1;
    public static final int STATE_LOAD_MORE_ERROR = 2;
    public static final int STATE_LOAD_MORE_NONE = 3;

    private LinearLayout ll_load_more;
    private TextView tv_load_error;

    public MoreHolder(boolean hasmore) {
        if (hasmore) {
            setData(STATE_LOAD_MORE);
        } else {
            setData(STATE_LOAD_MORE_NONE);
        }
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.list_item_more);
        ll_load_more = (LinearLayout) view.findViewById(R.id.ll_load_more);
        tv_load_error = (TextView) view.findViewById(R.id.tv_load_error);
        return view;
    }

    @Override
    public void refershView(Integer data) {
        switch (data) {
            case STATE_LOAD_MORE:
                //加载更多
                ll_load_more.setVisibility(View.VISIBLE);
                tv_load_error.setVisibility(View.GONE);
                break;
            case STATE_LOAD_MORE_NONE:
                //
                ll_load_more.setVisibility(View.GONE);
                tv_load_error.setVisibility(View.GONE);
                break;
            case STATE_LOAD_MORE_ERROR:
                ll_load_more.setVisibility(View.GONE);
                tv_load_error.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
