package com.magnatiposintegrationlibrary;

import androidx.annotation.NonNull;
import android.util.Log;
import android.app.Activity;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import com.marshal.fab.e285andlib.e285Interface;
import com.marshal.fab.e285Data.ConfigData;
import com.marshal.fab.e285andlib.ErrorCodes.ResponseCodeAndMsg;
import com.marshal.fab.e285andlib.ErrorCodes.TxnCode;
import com.marshal.fab.e285device.Device285;
import com.marshal.fab.security.Assistant;
import com.marshal.fab.security.Security;
import com.marshal.fab.e285andlib.VFIMembers;


@ReactModule(name = MagnatiPosIntegrationLibraryModule.NAME)
public class MagnatiPosIntegrationLibraryModule extends ReactContextBaseJavaModule {
  public static final String NAME = "MagnatiPosIntegrationLibrary";
  private static ReactApplicationContext reactContext;
  public static VFIMembers objVFIMembers = new VFIMembers();

  public MagnatiPosIntegrationLibraryModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void triggerPosInitialize(String uuid,  Integer connectionTimeout,Integer transactionTimeout, Integer settlementTimeout, boolean enableTrace, Promise promise) {
      VFIMembers var10000;
      String pLicense = "";
      Activity pActivity = getCurrentActivity();
      // do not change this
      String pUUID = uuid; //;"00001101-0000-1000-8000-00805F9B34FB";
      String pConnectionTimeout = connectionTimeout.toString(); // "30";
      String pTxnTimeout = transactionTimeout.toString(); // "120";
      String pSettlementTimeout = settlementTimeout.toString(); // "300";
      // to log trace boolean 0 or 1
      String pTrace = enableTrace ? "1" :"0";
      Log.d("POS_Initialize...","");
      try {
        var10000 = objVFIMembers;
        boolean inInitDone = e285Interface.VFI_Initialize(pActivity, pLicense, pUUID, pConnectionTimeout, pTxnTimeout, pSettlementTimeout, pTrace);
        Log.d("POS_Initialize completed...", " " + inInitDone);
        WritableMap map = Arguments.createMap();
        map.putString("message", VFIMembers.getVFI_ReponseMessage());
        map.putString("code", VFIMembers.getVFI_ResponseCode());
        promise.resolve(map);
      } catch (Exception ex) {
        WritableMap map = Arguments.createMap();
        map.putString("message", ex.toString());
        map.putString("code", "XXXXX");
        promise.resolve(map);
      }
    promise.resolve(uuid);

  }

}
