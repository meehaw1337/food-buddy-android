package mkomar.foodbuddy.services.web;

import java.io.IOException;
import java.util.List;

import mkomar.foodbuddy.model.UserProduct;
import mkomar.foodbuddy.services.FoodbuddyAPI;
import mkomar.foodbuddy.services.interfaces.UserProductsService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class UserProductsWebService implements UserProductsService {

    private FoodbuddyAPI foodbuddyAPI;

    private static UserProductsWebService instance;

    public static UserProductsWebService getUserProductsWebProvider() {
        if (instance == null) {
            instance = new UserProductsWebService();
        }

        return instance;
    }

    private UserProductsWebService() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.5:3000")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient)
                .build();

        this.foodbuddyAPI = retrofit.create(FoodbuddyAPI.class);
    }

    @Override
    public List<UserProduct> getUsersProducts(Long userId) {
        try {
            return foodbuddyAPI.getUsersProducts(userId).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateUsersProductQuantity(Long userId, Long userProductId, Long updatedQuantity) {
        try {
            foodbuddyAPI.updateUsersProductQuantity(userId, userProductId, updatedQuantity).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
