package com.magnatiposintegrationlibrary;

import android.util.Log;
import android.app.Activity;

import com.facebook.react.bridge.Promise;

import com.marshal.fab.e285andlib.e285Interface;
import com.marshal.fab.e285andlib.VFIMembers;

class TaskPOSStopTransactionMode implements Runnable {
  public static VFIMembers objVFIMembers = new VFIMembers();
  private Activity currentActivity;
  private Promise promise;

  public TaskPOSStopTransactionMode(Activity currentActivity, Promise promise) {
    this.currentActivity = currentActivity;
    this.promise = promise;
  }

  @Override
  public void run() {
    Response response;
    try {
      VFIMembers var10000;
      Log.d("POS_StopTransactionMode...", "");
      var10000 = objVFIMembers;
      boolean isTransactionStopped = e285Interface.VFI_StopTransctionMode(currentActivity);
      Log.d("POS_StopTransactionMode completed...", " " + isTransactionStopped);
      response = new Response(isTransactionStopped, VFIMembers.getVFI_ResponseCode(), VFIMembers.getVFI_ReponseMessage());
    } catch (Exception ex) {
      Log.d("POS_StopTransactionMode failed...", " " + ex.toString());
      ex.printStackTrace();
      response = new Response(false, "XXXX", ex.toString());
    }
    promise.resolve(response.getResponse());
  }
}
