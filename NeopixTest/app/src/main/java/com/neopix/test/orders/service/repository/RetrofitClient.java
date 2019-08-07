package com.neopix.test.orders.service.repository;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.neopix.test.orders.di.ViewModelSubComponent;
import com.neopix.test.orders.viewmodel.OrderViewModelFactory;

import java.lang.reflect.Type;
import java.util.Date;

import javax.inject.Singleton;

import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
  private static final String BASE_URL = "https://pixabay.com/";
  private static RetrofitClient mInstance;
  private Retrofit retrofit;


  private RetrofitClient() {

    Gson gson = new GsonBuilder()
      .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
          JsonParseException {
          return new Date(json.getAsJsonPrimitive().getAsLong());
        }
      }).setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


    retrofit = new Retrofit.Builder()
      .baseUrl(NeopixService.HTTP_API_NEOPIX_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .client(client)
      .build();
  }

  public static synchronized RetrofitClient getInstance() {
    if (mInstance == null) {
      mInstance = new RetrofitClient();
    }
    return mInstance;
  }

  public NeopixService getApi() {
    return retrofit.create(NeopixService.class);
  }
}
