package com.neopix.test.orders.di;

import com.neopix.test.orders.service.repository.NeopixService;
import com.neopix.test.orders.viewmodel.OrderViewModelFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import javax.inject.Singleton;

import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Type;
import java.util.Date;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(subcomponents = ViewModelSubComponent.class)
class AppModule {

    Gson gson = new GsonBuilder()
      .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws
        JsonParseException {
            return new Date(json.getAsJsonPrimitive().getAsLong());
        }
    }).setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    @Singleton @Provides
    NeopixService provideGithubService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        return new Retrofit.Builder()
                .baseUrl(NeopixService.HTTP_API_NEOPIX_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(NeopixService.class);
    }

    @Singleton
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(
            ViewModelSubComponent.Builder viewModelSubComponent) {
        return new OrderViewModelFactory(viewModelSubComponent.build());
    }
}
