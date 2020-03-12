package mkomar.foodbuddy.services.web;

import mkomar.foodbuddy.services.FoodbuddyAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class FoodbuddyAPIProvider {

    private static FoodbuddyAPI instance;

    private static void buildAPIInstance() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.114:3000")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient)
                .build();

        instance = retrofit.create(FoodbuddyAPI.class);
    }

    public static FoodbuddyAPI get() {
        if (instance == null) {
            buildAPIInstance();
        }

        return instance;
    }
}
