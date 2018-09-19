package com.xhidebatterysaver.root;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import static de.robv.android.xposed.XposedHelpers.findClass;
import de.robv.android.xposed.XposedHelpers;

public class XHideBatterySaverClass implements IXposedHookLoadPackage  {

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if(!lpparam.packageName.equals("com.android.systemui"))
            return;
        XposedBridge.log("XHideBatterySaver: found target package ");
        XposedBridge.hookAllConstructors(
                findClass("com.android.systemui.statusbar.phone.BarTransitions$BarBackgroundDrawable",lpparam.classLoader),
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
						XposedHelpers.setIntField(param.thisObject,"mPowerSaveWarning",0);
						// Or use value from another field : 
                        //int targetVal = XposedHelpers.getIntField(param.thisObject,"mTransparent");
                        //XposedHelpers.setIntField(param.thisObject,"mPowerSaveWarning",targetVal);
                        XposedBridge.log("XHideBatterySaverClass: Patched ! ");
                    }
                });
    }
}