package com.coink.plugins.pelicano;

import com.getcapacitor.JSObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hardware.pelicano.Response_t;
import hardware.pelicano.TestStatus_t;

public class PelicanoResponse extends JSObject {

  private final String message;
  private final int statusCode;

  public PelicanoResponse(Response_t response) {
    super();
    message = response.getMessage();
    statusCode = response.getStatusCode();
    put("statusCode", statusCode);
    put("message", message);
  }

  public PelicanoResponse(Response_t response, CoinsChannels channels) {
    super();
    message = response.getMessage();
    statusCode = response.getStatusCode();
    put("statusCode", statusCode);
    put("message", message);
    put("channels", channels);
  }

  public PelicanoResponse(TestStatus_t status) {
    super();
    Date now = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
    String date = df.format(now);

    message = status.getMessage();
    statusCode = status.getErrorCode();

    put("version", status.getVersion());
    put("device", status.getDevice());
    put("errorType", status.getErrorType());
    put("errorCode", status.getErrorCode());
    put("message", status.getMessage());
    put("aditionalInfo", status.getAditionalInfo());
    put("priority", status.getPriority());
    put("date", date);
  }

  public String getMessage() {
    return message;
  }

  public int getStatusCode() {
    return statusCode;
  }

}
