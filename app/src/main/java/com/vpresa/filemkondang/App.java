package com.vpresa.filemkondang;

import android.app.Application;

import org.greenrobot.eventbus.EventBus;

import com.vpresa.filemkondang.restapi.RestAPIInterface;
import com.vpresa.filemkondang.restapi.RestAPIURL;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by Dwicandra on 9/1/2017.
 */

public class App extends Application {
    private static App instance;
    private static Retrofit retrofit;
    private EventBus eventBus;

    public App() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        createEventBus();
        createRetrofitClient();

    }

    private void createRetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(RestAPIURL.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofitClient() {
        return retrofit;
    }

    private void createEventBus() {
        eventBus = EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .addIndex(new MyEventBusIndex())
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public RestAPIInterface getApiService() {
        return getRetrofitClient().create(RestAPIInterface.class);
    }
}
