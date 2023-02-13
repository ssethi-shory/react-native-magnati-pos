package com.magnatiposintegrationlibrary;

import android.util.Log;
import android.app.Activity;

import com.facebook.react.bridge.Promise;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import com.marshal.fab.e285andlib.e285Interface;
import com.marshal.fab.e285andlib.VFIMembers;

class TaskPOSSettleBatch implements Runnable {
  public static VFIMembers objVFIMembers = new VFIMembers();
  private Activity currentActivity;
  private String tid;
  private Promise promise;

  public TaskPOSSettleBatch(Activity currentActivity, String tid, Promise promise) {
    this.currentActivity = currentActivity;
    this.promise = promise;
    this.tid = tid;
  }

  void setTransactionDetails() {
    VFIMembers var10000;
    var10000 = objVFIMembers;
    VFIMembers.setVFI_TID(tid);
  }

  @Override
  public void run() {
    Response response;
    try {
      setTransactionDetails();
      Log.d("POS_SettleBatch...", "");
      boolean isBatchSettled = e285Interface.VFI_Settlement(currentActivity);
      Log.d("POS_SettleBatch completed...", " " + isBatchSettled);
      WritableMap data = Arguments.createMap();
      data.putString("VFI_DBCount", VFIMembers.getVFI_DBCount());
      data.putString("VFI_CRCount", VFIMembers.getVFI_CRCount());
      data.putString("VFI_DBAmount", VFIMembers.getVFI_DBAmount());
      data.putString("VFI_CRAmount", VFIMembers.getVFI_CRAmount());
      data.putString("VFI_Batch", VFIMembers.getVFI_Batch());
      data.putString("VFI_RRN", VFIMembers.getVFI_RRN());
      response = new Response(isBatchSettled, VFIMembers.getVFI_ResponseCode(), VFIMembers.getVFI_ReponseMessage(),
          data);
    } catch (Exception ex) {
      Log.d("POS_SettleBatch failed...", " " + ex.toString());
      ex.printStackTrace();
      response = new Response(false, "XXXX", ex.toString());
    }
    promise.resolve(response.getResponse());
  }
}
