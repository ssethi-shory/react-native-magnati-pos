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
      String pUUID = uuid;
      String pConnectionTimeout = connectionTimeout.toString();
      String pTxnTimeout = transactionTimeout.toString();
      String pSettlementTimeout = settlementTimeout.toString();
      // to log trace boolean 0 or 1
      String pTrace = enableTrace ? "1" :"0";
      Log.d("POS_Initialize...","");
      try {
        var10000 = objVFIMembers;
        boolean inInitDone = e285Interface.VFI_Initialize(pActivity, pLicense, pUUID, pConnectionTimeout, pTxnTimeout, pSettlementTimeout, pTrace);
        Log.d("POS_Initialize completed...", " " + inInitDone);
        WritableMap map = Arguments.createMap();
        map.putBoolean("success", inInitDone);
        map.putString("message", VFIMembers.getVFI_ReponseMessage());
        map.putString("code", VFIMembers.getVFI_ResponseCode());
        promise.resolve(map);
      } catch (Exception ex) {
        WritableMap map = Arguments.createMap();
        map.putBoolean("success", false);
        map.putString("message", ex.toString());
        map.putString("code", "XXXXX");
        promise.resolve(map);
      }
  }

  @ReactMethod
  public void startTransactionMode(Promise promise) {
    VFIMembers var10000;
    Activity pActivity = getCurrentActivity();
    Log.d("POS_StartTransactionMode...","");
    try {
      var10000 = objVFIMembers;
      boolean isTransactionStarted = e285Interface.VFI_StartTransctionMode(getCurrentActivity());
      Log.d("POS_StartTransactionMode completed...", " " + isTransactionStarted);
      WritableMap map = Arguments.createMap();
      map.putBoolean("success", isTransactionStarted);
      map.putString("message", VFIMembers.getVFI_ReponseMessage());
      map.putString("code", VFIMembers.getVFI_ResponseCode());
      resetResponse();
      promise.resolve(map);
    } catch (Exception ex) {
      WritableMap map = Arguments.createMap();
      map.putBoolean("success", false);
      map.putString("message", ex.toString());
      map.putString("code", "XXXXX");
      promise.resolve(map);
    }
  }

  private void resetResponse() {
    VFIMembers var10000;
    VFIMembers.setVFI_ResponseCode("");
    VFIMembers.setVFI_ReponseMessage("");
  }
  @ReactMethod
  public void getAuthorization(Integer amount,String tid,String receiptNo,Promise promise) {
    VFIMembers var10000;
    Activity pActivity = getCurrentActivity();
    String TXNTYPE ="1";
    Log.d("POS_GetAuthorization...","");
    try {
      var10000 = objVFIMembers;
      VFIMembers.setVFI_TXNTYPE("1");
      VFIMembers.setVFI_TXNAMT(amount.toString());
      VFIMembers.setVFI_TID(tid);
      VFIMembers.setVFI_ECRNO(receiptNo);

      boolean isAuth = e285Interface.VFI_GetAuthorization(getCurrentActivity());
      Log.d("POS_GetAuthorization completed...", " " + isAuth);
      Log.d("POS_GetAuthorization completed...", " " + VFIMembers.getVFI_ReponseMessage());
      Log.d("POS_GetAuthorization completed...", " " + VFIMembers.getVFI_ResponseCode());

      WritableMap map = Arguments.createMap();
      map.putBoolean("success", isAuth && VFIMembers.getVFI_ResponseCode().equals("000"));
      map.putString("message", VFIMembers.getVFI_ReponseMessage());
      map.putString("code", VFIMembers.getVFI_ResponseCode());

      WritableMap data = Arguments.createMap();
      if(isAuth && VFIMembers.getVFI_ResponseCode().equals("000")){
        data.putString("VFI_TXNTYPE", VFIMembers.getVFI_TXNTYPE());
        data.putString("VFI_ECRNO", VFIMembers.getVFI_ECRNO());
        data.putString("VFI_TXNAMT", VFIMembers.getVFI_TXNAMT());
        data.putString("VFI_Expiry", VFIMembers.getVFI_Expiry());
        data.putString("VFI_CHName", VFIMembers.getVFI_CHName());
        data.putString("VFI_MESSNO", VFIMembers.getVFI_MESSNO());
        data.putString("VFI_TransSource", VFIMembers.getVFI_TransSource());
        data.putString("VFI_AuthMode", VFIMembers.getVFI_AuthMode());
        data.putString("VFI_CHVerification", VFIMembers.getVFI_CHVerification());
        data.putString("VFI_ApprovalCode", VFIMembers.getVFI_ApprovalCode());
        data.putString("VFI_EMVLabel", VFIMembers.getVFI_EMVLabel());
        data.putString("VFI_EMVAID", VFIMembers.getVFI_EMVAID());
        data.putString("VFI_EMVTVR", VFIMembers.getVFI_EMVTVR());
        data.putString("VFI_EMVTSI", VFIMembers.getVFI_EMVTSI());
        data.putString("VFI_EMVAC", VFIMembers.getVFI_EMVAC());
        data.putString("VFI_EMVCID", VFIMembers.getVFI_EMVCID());
        data.putString("VFI_AdditionalInfo", VFIMembers.getVFI_AdditionalInfo());
        data.putString("VFI_CashAmount", VFIMembers.getVFI_CashAmount());
        data.putString("VFI_TID", VFIMembers.getVFI_TID());
        data.putString("VFI_Batch", VFIMembers.getVFI_Batch());
        data.putString("VFI_Mid", VFIMembers.getVFI_Mid());
        data.putString("VFI_RRN", VFIMembers.getVFI_RRN());
        data.putString("VFI_PYCExchangeRate", VFIMembers.getVFI_PYCExchangeRate());
        data.putString("VFI_PYCAmount", VFIMembers.getVFI_PYCAmount());
        data.putString("VFI_PYCMarkup", VFIMembers.getVFI_PYCMarkup());
        data.putString("VFI_PYCCurrencyCode", VFIMembers.getVFI_PYCCurrencyCode());
        data.putString("VFI_PYCCurrencyName", VFIMembers.getVFI_PYCCurrencyName());
        data.putString("VFI_PYCOPTInOut", VFIMembers.getVFI_PYCOPTInOut());
        data.putString("VFI_QVEPS", VFIMembers.getVFI_QVEPS());
        data.putString("VFI_QRVoucherNo", VFIMembers.getVFI_QRVoucherNo());
        data.putString("VFI_PayByMerchantOrderNo", VFIMembers.getVFI_PayByMerchantOrderNo());
        data.putString("VFI_PayByOrderNo", VFIMembers.getVFI_PayByOrderNo());
      }
      map.putMap("data", data);
      resetResponse();
      promise.resolve(map);
    } catch (Exception ex) {
      WritableMap map = Arguments.createMap();
      map.putBoolean("success", false);
      map.putString("message", ex.toString());
      map.putString("code", "XXXXX");
      promise.resolve(map);
    }
  }

  @ReactMethod
  public void stopTransactionMode(Promise promise) {
    VFIMembers var10000;
    String pLicense = "";
    Activity pActivity = getCurrentActivity();
    Log.d("POS_StopTransactionMode...","");
    try {
      var10000 = objVFIMembers;
      boolean isTransactionStopped = e285Interface.VFI_StopTransctionMode(getCurrentActivity());
      Log.d("POS_StopTransactionMode completed...", " " + isTransactionStopped);
      WritableMap map = Arguments.createMap();
      map.putBoolean("success", isTransactionStopped);
      map.putString("message", VFIMembers.getVFI_ReponseMessage());
      map.putString("code", VFIMembers.getVFI_ResponseCode());
      resetResponse();
      promise.resolve(map);
    } catch (Exception ex) {
      WritableMap map = Arguments.createMap();
      map.putBoolean("success", false);
      map.putString("message", ex.toString());
      map.putString("code", "XXXXX");
      promise.resolve(map);
    }
  }

}
