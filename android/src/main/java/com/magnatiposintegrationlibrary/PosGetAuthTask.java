package com.magnatiposintegrationlibrary;

import android.util.Log;
import android.app.Activity;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import com.marshal.fab.e285andlib.e285Interface;
import com.marshal.fab.e285andlib.VFIMembers;

import java.util.concurrent.Callable;

public class PosGetAuthTask implements Callable<Response> {
  public static VFIMembers objVFIMembers = new VFIMembers();
  private Activity currentActivity;
  private Integer amount;
  private String tid;
  private String receiptNo;

  public PosGetAuthTask(Activity currentActivity, Integer amount, String tid, String receiptNo) {
    this.currentActivity = currentActivity;
    this.amount = amount;
    this.tid = tid;
    this.receiptNo = receiptNo;
  }

  void setTransactionDetails() {
    VFIMembers var10000;
    var10000 = objVFIMembers;
    VFIMembers.setVFI_TXNTYPE("1");
    VFIMembers.setVFI_TXNAMT(amount.toString());
    VFIMembers.setVFI_TID(tid);
    VFIMembers.setVFI_ECRNO(receiptNo);
  }

  Response getTransactionResponse(boolean isAuthCompleted) {
    VFIMembers var10000;
    var10000 = objVFIMembers;
    WritableMap data = Arguments.createMap();
    data.putString("VFI_TXNTYPE", VFIMembers.getVFI_TXNTYPE());
    data.putString("VFI_ECRNO", VFIMembers.getVFI_ECRNO());
    data.putString("VFI_TXNAMT", VFIMembers.getVFI_TXNAMT());
    data.putString("VFI_Expiry", VFIMembers.getVFI_Expiry());
    data.putString("VFI_CardNumber", VFIMembers.getVFI_CardNumber());
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
    data.putString("VFI_DeviceSerialNo", VFIMembers.getVFI_DeviceSerialNo());
    data.putString("VFI_DateTime", VFIMembers.getVFI_DateTime());
    data.putString("VFI_CardSchemeName", VFIMembers.getVFI_CardSchemeName());
    return new Response(isAuthCompleted, VFIMembers.getVFI_ResponseCode(), VFIMembers.getVFI_ReponseMessage(), data);
  }

  public Response call() {
    try {
      setTransactionDetails();
      Log.d("POS_GetAuthorization inside callable...", "");
      boolean isAuth = e285Interface.VFI_GetAuthorization(currentActivity);
      Log.d("POS_GetAuthorization inside completed...", ""+isAuth);
      return getTransactionResponse(isAuth);
    } catch (Exception ex) {
      Log.d("POS_Exception", ex.toString());
      return new Response(false, "1111", ex.toString() + "VFIMembers.getVFI_ReponseMessage()");
    }
  }
}
