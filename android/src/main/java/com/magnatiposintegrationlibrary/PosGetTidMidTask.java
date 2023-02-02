package com.magnatiposintegrationlibrary;

import android.util.Log;
import android.app.Activity;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import com.marshal.fab.e285andlib.e285Interface;
import com.marshal.fab.e285andlib.VFIMembers;

import java.util.concurrent.Callable;

public class PosGetTidMidTask implements Callable<Response> {
  public static VFIMembers objVFIMembers = new VFIMembers();
  private Activity currentActivity;

  public PosGetTidMidTask(Activity currentActivity) {
    this.currentActivity = currentActivity;
  }

  Response getTransactionResponse(boolean isGetTIDMIDCompleted) {
    VFIMembers var10000;
    var10000 = objVFIMembers;
    WritableMap data = Arguments.createMap();
    data.putString("VFI_TID", VFIMembers.getVFI_TID());
    data.putString("VFI_Mid", VFIMembers.getVFI_Mid());
    return new Response(isGetTIDMIDCompleted, VFIMembers.getVFI_ResponseCode(), VFIMembers.getVFI_ReponseMessage(), data);
  }

  public Response call() {
    try {
      VFIMembers var10000;
      Log.d("POS_GetTIDMID inside callable ...", "");
      var10000 = objVFIMembers;
      boolean isGetTIDMIDCompleted = e285Interface.VFI_GetMIDTID(currentActivity);
      Log.d("POS_GetTIDMID inside callable completed...", " " + isGetTIDMIDCompleted);
      return getTransactionResponse(isGetTIDMIDCompleted);
    } catch (Exception ex) {
      Log.d("POS_Exception", ex.toString());
      return new Response(false, "1111", ex.toString() + "VFIMembers.getVFI_ReponseMessage()");
    }
  }
}
