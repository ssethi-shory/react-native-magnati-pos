package com.magnatiposintegrationlibrary;

import android.util.Log;
import android.app.Activity;

import com.facebook.react.bridge.Promise;

import com.marshal.fab.e285andlib.e285Interface;
import com.marshal.fab.e285andlib.VFIMembers;

class TaskPOSInitialize implements Runnable {
  public static VFIMembers objVFIMembers = new VFIMembers();
  private String uuid;
  private String connectionTimeout;
  private String transactionTimeout;
  private String settlementTimeout;
  private String enableTrace;
  private Activity currentActivity;
  private Promise promise;

  public TaskPOSInitialize(String uuid, Integer connectionTimeout, Integer transactionTimeout, Integer settlementTimeout, boolean enableTrace, Activity currentActivity, Promise promise) {
    this.uuid = uuid;
    this.connectionTimeout = connectionTimeout.toString();
    this.transactionTimeout = transactionTimeout.toString();
    this.settlementTimeout = settlementTimeout.toString();
    this.enableTrace = enableTrace ? "1" : "0";
    this.currentActivity = currentActivity;
    this.promise = promise;
  }

  @Override
  public void run() {
    Response response;
    try {
      VFIMembers var10000;
      Log.d("POS_Initialize...", "");
      var10000 = objVFIMembers;
      boolean inInitDone = e285Interface.VFI_Initialize(currentActivity, "", uuid, connectionTimeout, transactionTimeout, settlementTimeout, enableTrace);
      Log.d("POS_Initialize completed...", " " + inInitDone);
      response = new Response(inInitDone, VFIMembers.getVFI_ResponseCode(), VFIMembers.getVFI_ReponseMessage());
    } catch (Exception ex) {
      Log.d("POS_PosGetTidMidTaskInitialize failed...", " " + ex.toString());
      ex.printStackTrace();
      response = new Response(false, "XXXX", ex.toString());
    }
    promise.resolve(response.getResponse());
  }
}
