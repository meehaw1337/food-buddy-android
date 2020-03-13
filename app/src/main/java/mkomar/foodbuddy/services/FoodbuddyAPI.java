package mkomar.foodbuddy.services;


import java.util.List;

import mkomar.foodbuddy.model.Category;
import mkomar.foodbuddy.model.Product;
import mkomar.foodbuddy.model.Recipe;
import mkomar.foodbuddy.model.UserProduct;
import mkomar.foodbuddy.model.UserRecipe;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FoodbuddyAPI {

    @GET("/users/{user_id}/products/")
    Call<List<UserProduct>> getUsersProducts(@Path("user_id") Long userId);

    @PUT("/users/{user_id}/products/{user_product_id}")
    Call<Void> updateUsersProductQuantity(@Path("user_id") Long userId,
                                          @Path("user_product_id") Long userProductId,
                                          @Query("updatedQuantity") Long updatedQuantity);

    @GET("/products/{category}/")
    Call<List<Product>> getProductsByCategory(@Path("category") String categoryName);

    @GET("/categories/")
    Call<List<Category>> getProductCategories();

    @GET("/recipes/")
    Call<List<Recipe>> getRecipes();

    @GET("/users/{user_id}/recipes/available/")
    Call<List<Recipe>> getUsersAvailableRecipes(@Path("user_id") Long userId);

    @GET("/users/{user_id}/recipes/favourite/")
    Call<List<UserRecipe>> getUsersFavouriteRecipes(@Path("user_id") Long userId);

    @POST("/users/{user_id}/recipes/favourite/")
    Call<Void> addRecipeToFavourites(@Path("user_id") Long userId, @Query("recipeId") Long recipeId);

    @DELETE("/users/{user_id}/recipes/favourite/")
    Call<Void> removeRecipeFromFavourites(@Path("user_id") Long userId, @Query("recipeId") Long recipeId);
}
