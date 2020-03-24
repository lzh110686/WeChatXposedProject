package com.example.wechatplugchange;

import android.view.Menu;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        String hookClass = "com.tencent.mm.sdk.platformtools.bu";//显示要hook的类名
        String hookMethodName = "iQ";//显示要hook的方法
        XposedHelpers.findAndHookMethod (hookClass, lpparam.classLoader, hookMethodName,int.class,int.class,new XC_MethodHook () {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod (param);
                XposedBridge.log ("Hook成功了！！！");
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod (param);
                if((int)param.args[0] == 2){
                    param.setResult(1);
                }else if((int)param.args[0] == 5){
                    param.setResult(5);// 总返回6点
                }
            }
        });
    }
}
