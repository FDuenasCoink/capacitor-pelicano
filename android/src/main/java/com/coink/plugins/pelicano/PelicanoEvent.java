package com.coink.plugins.pelicano;

import com.getcapacitor.JSObject;

import hardware.pelicano.CoinError_t;

public class PelicanoEvent extends JSObject {

  public PelicanoEvent(CoinError_t coin) {
    super();
    int value = coin.getCoin();
    put("value", value);
  }

  private PelicanoEvent(int value) {
    super();
    put("value", value);
  }

  private PelicanoEvent(CoinError_t coin, String type) {
    super();
    String message = coin.getMessage();
    int code = coin.getStatusCode();
    if (type.equals("error")) {
      JSObject error = new JSObject();
      error.put("message", message);
      error.put("code", code);
      put("error", error);
    } else if (type.equals("warning")) {
      put("code", code);
      put("message", message);
    }
  }

  public static PelicanoEvent error(CoinError_t coin) {
    return new PelicanoEvent(coin, "error");
  }

  public static PelicanoEvent warning(CoinError_t coin) {
    return new PelicanoEvent(coin, "warning");
  }

  public static PelicanoEvent fromValue(int value) {
    return new PelicanoEvent(value);
  }

}
