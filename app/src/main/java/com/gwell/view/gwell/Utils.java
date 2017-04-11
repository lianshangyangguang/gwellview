package com.gwell.view.gwell;

import android.content.Context;

/**
 * Created by xiyingzhu on 2017/4/7.
 */
public class Utils {
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
