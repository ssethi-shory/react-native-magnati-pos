package com.magnatiposintegrationlibrary;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Callback;

public class Response {
  WritableMap response;

  public Response(Boolean success, String code, String message) {
    WritableMap map = Arguments.createMap();
    map.putBoolean("success", success);
    map.putString("message", message);
    map.putString("code", code);
    this.response = map;
  }

  public Response(Boolean success, String code, String message, WritableMap data) {
    WritableMap map = Arguments.createMap();
    map.putBoolean("success", success);
    map.putString("message", message);
    map.putString("code", code);
    map.putMap("data", data);
    this.response = map;
  }

  public WritableMap getResponse() {
    return this.response;
  }
}
