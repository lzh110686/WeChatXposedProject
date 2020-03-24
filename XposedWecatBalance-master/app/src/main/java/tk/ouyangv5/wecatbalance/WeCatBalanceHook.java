package tk.ouyangv5.wecatbalance;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Field;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class WeCatBalanceHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        // 微信首页添加按钮
       XposedHelpers.findAndHookMethod ("com.tencent.mm.plugin.wallet.balance.ui.WalletBalanceManagerUI", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook () {
           @Override
           protected void afterHookedMethod(MethodHookParam param) throws Throwable {
               super.afterHookedMethod (param);
               XposedBridge.log ("");
               //得到当前类的对象
               Activity thisObject=(Activity)param.thisObject;
               //得到当前类对象的textView属性
               final TextView textView = (TextView) XposedHelpers.getObjectField (thisObject, "sxb");
               textView.setText ("$99999999");
               //添加textView变化的监听器
               textView.addTextChangedListener (new TextWatcher () {
                   @Override
                   public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                   }

                   @Override
                   public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                   }

                   @Override
                   public void afterTextChanged(Editable editable) {
                       //监听到发生改变后
                       textView.removeTextChangedListener (this);
                       textView.setText ("$99999.99");
                       textView.addTextChangedListener (this);
                   }
               });
           }
       });

    }
}
