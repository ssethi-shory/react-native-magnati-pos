package com.magnatiposintegrationlibrary;

import android.util.Log;
import android.app.Activity;

import com.facebook.react.bridge.Promise;

import com.marshal.fab.e285andlib.e285Interface;
import com.marshal.fab.e285andlib.VFIMembers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;

class PosGetTidMidExecutor implements Runnable {
  ExecutorService singleThreadExecutor = null;
  private Activity currentActivity;
  private Promise promise;
  private Integer timeoutInSec;

  public PosGetTidMidExecutor(Integer timeoutInSec, Activity currentActivity, Promise promise) {
    this.timeoutInSec = timeoutInSec;
    this.currentActivity = currentActivity;
    this.promise = promise;
  }

  @Override
  public void run() {
    Response response;
    singleThreadExecutor = Executors.newSingleThreadExecutor();
    final Future<Response> handler = singleThreadExecutor.submit(new PosGetTidMidTask(currentActivity));
    try {
      response = handler.get(timeoutInSec, TimeUnit.SECONDS);
    } catch (TimeoutException | InterruptedException | ExecutionException ex) {
      Log.d("POS_GetTIDMID failed...", " " + ex.toString());
      ex.printStackTrace();
      boolean isCancelled = handler.isCancelled();
      if (!isCancelled) handler.cancel(true);
      singleThreadExecutor.shutdown();
      response = new Response(false, "1111", "POS Connection Timed out");
    } finally {
      boolean isDone = handler.isDone();
      boolean isCancelled = handler.isCancelled();
      Log.i("POS_GetTIDMID info is done in finally", isDone + "");
      Log.i("POS_GetTIDMID info is cancelled in finally", isCancelled + "");
      try {
        if (!singleThreadExecutor.isTerminated()) {
          singleThreadExecutor.shutdown();
          singleThreadExecutor.awaitTermination(1, TimeUnit.SECONDS);
        }
        Log.i("POS_GetTIDMID info is service terminated in finally", singleThreadExecutor.isTerminated() + "");
        handler.cancel(true);
        Log.i("POS_GetTIDMID serice shutdown finished in finally", "");
      } catch (InterruptedException ex) {
        ex.printStackTrace();
        Log.d("POS_GetTIDMID serice shutdown error closing error", ex.toString());
      }
    }
    promise.resolve(response.getResponse());
  }
}
