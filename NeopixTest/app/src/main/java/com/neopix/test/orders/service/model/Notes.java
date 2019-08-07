package com.neopix.test.orders.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Notes {
  @SerializedName("message")
  public String message;

  @SerializedName("date")
  public Date date;
}
