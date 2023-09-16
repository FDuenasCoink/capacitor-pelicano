package com.coink.plugins.pelicano;

public class PelicanoException extends Exception{
  private String code;

  public PelicanoException(String message, int code) {
    super(message);
    this.code = Integer.toString(code);
  }

  public String getCode() {
    return code;
  }
}
