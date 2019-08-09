package com.neopix.test.orders.service.repository;

import com.neopix.test.orders.service.model.OrderDetails;
import com.neopix.test.orders.service.model.Orders;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface NeopixService {
    String HTTP_API_NEOPIX_URL = "http://mobile-test.neopixdev.com/";

    @GET("orders")
    Call<Orders> getOrderList(@Query("nextId") int page, @Query("limit") String perPage);

    @GET("orders/{orderId}")
    Call<OrderDetails> getOrderDetails(@Path("orderId") String orderId);

    @GET("orders")
    Call<Orders> getOrderList();
}
