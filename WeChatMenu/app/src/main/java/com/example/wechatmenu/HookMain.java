package com.example.wechatmenu;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookMain implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        String hookClass = "com.tencent.mm.ui.LauncherUI";//显示要hook的类名
        String hookMethodName = "onCreateOptionsMenu";//显示要hook的方法
        //TODO findAndHookMethod方法的参数（要Hook的包名+类名，Classload，要hook的方法名，hook方法的参数，XC_MethodHook匿名类）
        XposedHelpers.findAndHookMethod(hookClass, lpparam.classLoader, hookMethodName, Menu.class, new XC_MethodHook () {

            @Override

            protected void beforeHookedMethod(final MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                final Context applicationContext;
                //将hook到的微信里面的Menu参数传给一个新构造的menu对象
                Menu menu = (Menu) param.args[0];
                applicationContext=AndroidAppHelper.currentApplication().getApplicationContext();
                XposedBridge.log ("获取上下文成功");
                //使用menu类的add方法添加按钮
                menu.add(0, 3, 0, "1");
                menu.add(0, 4, 0, "2");
                //给menu添加点击
                // 事件
                for (int i = 0; i < menu.size(); i++) {
                    final int finalI = i;
                    menu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            XposedBridge.log("点击了: " + finalI + "");
                            Toast.makeText (applicationContext, "你点击了"+finalI+"", Toast.LENGTH_SHORT).show ();
                            return false;
                        }
                    });
                }
            }

        });

    }
}
