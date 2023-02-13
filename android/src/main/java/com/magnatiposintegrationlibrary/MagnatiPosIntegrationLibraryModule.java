package com.magnatiposintegrationlibrary;

import androidx.annotation.NonNull;

import android.app.Activity;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import com.facebook.react.bridge.Callback;

@ReactModule(name = MagnatiPosIntegrationLibraryModule.NAME)
public class MagnatiPosIntegrationLibraryModule extends ReactContextBaseJavaModule {
  public static final String NAME = "MagnatiPosIntegrationLibrary";
  private static ReactApplicationContext reactContext;
  private final Executor executor;

  public MagnatiPosIntegrationLibraryModule(ReactApplicationContext reactContext, Executor executor) {
    super(reactContext);
    this.executor = executor;
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void triggerPosInitialize(String uuid, Integer connectionTimeout, Integer transactionTimeout,
      Integer settlementTimeout, boolean enableTrace, Promise promise) {
    executor.execute(new TaskPOSInitialize(uuid, connectionTimeout, transactionTimeout, settlementTimeout, enableTrace,
        getCurrentActivity(), promise));
  }

  @ReactMethod
  public void startTransactionMode(Promise promise) {
    executor.execute(new TaskPOSStartTransactionMode(getCurrentActivity(), promise));
  }

  @ReactMethod
  public void getAuthorization(Integer amount, String tid, String receiptNo, Integer timeoutInSec, Promise promise) {
    executor.execute(new PosGetAuthExecutor(timeoutInSec, amount, tid, receiptNo, getCurrentActivity(), promise));
  }

  @ReactMethod
  public void stopTransactionMode(Promise promise) {
    executor.execute(new TaskPOSStopTransactionMode(getCurrentActivity(), promise));
  }

  @ReactMethod
  public void getTIDMID(Integer timeoutInSec, Promise promise) {
    executor.execute(new PosGetTidMidExecutor(timeoutInSec, getCurrentActivity(), promise));
  }

  @ReactMethod
  public void settleBatch(String tid, Promise promise) {
    executor.execute(new TaskPOSSettleBatch(getCurrentActivity(), tid, promise));
  }
}
