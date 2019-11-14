package com.pangutiandi.flutter_meiqia.mq;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.imageloader.MQImage;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;

/**
 * @function 美洽管理
 */
public final class MQManage {

    private MQManage() {
    }

    public static void initMeiqiaSDK(final Activity activity) {
        // 替换成自己的key
        MQConfig.init(activity, "", new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
                MQImage.setImageLoader(new MQGlideImageLoader4());
                Intent intent = new MQIntentBuilder(activity).build();
                activity.startActivity(intent);
            }

            @Override
            public void onFailure(int code, String message) {
                Log.e("Meiqia init fail, %s", message);
            }
        });

        // 可选
        // 配置自定义信息
//        MQConfig.ui.titleGravity = MQConfig.ui.MQTitleGravity.LEFT;
//        MQConfig.ui.backArrowIconResId = android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha;
//        MQConfig.ui.titleBackgroundResId = R.color.test_red;
//        MQConfig.ui.titleTextColorResId = R.color.test_blue;
//        MQConfig.ui.leftChatBubbleColorResId = R.color.test_green;
//        MQConfig.ui.leftChatTextColorResId = R.color.test_red;
//        MQConfig.ui.rightChatBubbleColorResId = R.color.test_red;
//        MQConfig.ui.rightChatTextColorResId = R.color.test_green;
//        MQConfig.ui.robotEvaluateTextColorResId = R.color.test_red;
//        MQConfig.ui.robotMenuItemTextColorResId = R.color.test_blue;
//        MQConfig.ui.robotMenuTipTextColorResId = R.color.test_blue;
    }
}
