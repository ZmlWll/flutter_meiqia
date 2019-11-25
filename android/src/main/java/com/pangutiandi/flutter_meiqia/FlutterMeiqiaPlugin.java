package com.pangutiandi.flutter_meiqia;

import android.content.Intent;
import android.util.Log;

import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.imageloader.MQImage;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.pangutiandi.flutter_meiqia.mq.MQGlideImageLoader4;
import com.pangutiandi.flutter_meiqia.mq.MQManage;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterMeiqiaPlugin */
public class FlutterMeiqiaPlugin implements MethodCallHandler {


  static Registrar registrar1;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_meiqia");
    channel.setMethodCallHandler(new FlutterMeiqiaPlugin());
    registrar1 = registrar;
  }

  @Override
  public void onMethodCall(MethodCall call, final Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }
    else if (call.method.equals("initMeiQia")) {
      String appKey = call.argument("appKey");
      // 替换成自己的key
      MQConfig.init(registrar1.activity(), appKey, new OnInitCallback() {
        @Override
        public void onSuccess(String clientId) {
          result.success("MeiQia 初始化成功");
        }

        @Override
        public void onFailure(int code, String message) {
          Log.e("Meiqia init fail, %s", message);
          result.success("MeiQia 初始化失败");
        }
      });
    }
    else if (call.method.equals("openMeiQia")) {
      MQImage.setImageLoader(new MQGlideImageLoader4());
      goToChat(call);
    }
    else {
      result.notImplemented();
    }
  }

  private void goToChat(MethodCall call) {
    MQIntentBuilder builder = new MQIntentBuilder(registrar1.activity());
    if (call.hasArgument("id")) {
      builder.setCustomizedId((String) call.argument("id"));
    }
    if (call.arguments != null) {
      if (call.hasArgument("userInfo")) {
        if (call.hasArgument("isUpdate")) {
          builder.updateClientInfo((HashMap<String, String>) call.argument("userInfo"));
        }else{
          builder.setClientInfo((HashMap<String, String>) call.argument("userInfo"));
        }
      }
    }
    Intent intent = builder.build();
    registrar1.activity().startActivity(intent);
  }
}
