package mkomar.foodbuddy.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mkomar.foodbuddy.R;
import mkomar.foodbuddy.model.UserRecipe;
import mkomar.foodbuddy.services.FoodbuddyAPI;
import mkomar.foodbuddy.services.web.FoodbuddyAPIProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailsActivity extends AppCompatActivity {

    @BindView(R.id.recipe_name_text_view)
    TextView recipeNameTextView;

    @BindView(R.id.recipe_description_text_view)
    TextView recipeDescriptionTextView;

    @BindView(R.id.recipe_image)
    ImageView recipeImageView;

    private Long recipeId;

    private FoodbuddyAPI foodbuddyAPI;

    private boolean isFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        foodbuddyAPI = FoodbuddyAPIProvider.get();

        ButterKnife.bind(this);

        recipeId = getIntent().getLongExtra("id", -0);

        if (getIntent().getStringExtra("name") != null) {
            recipeNameTextView.setText(getIntent().getStringExtra("name"));
        }

        if (getIntent().getStringExtra("description") != null) {
            recipeDescriptionTextView.setText(getIntent().getStringExtra("description"));
        }

        if (getIntent().getStringExtra("image_url") != null) {
            Picasso.get().load(getIntent().getStringExtra("image_url")).into(recipeImageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem favButton = menu.add(R.string.fav_button);

        // TODO remove hardcoded user ID
        /* Check if this recipe is one of user's favourite recipes */
        foodbuddyAPI.getUsersFavouriteRecipes(1L).enqueue(new Callback<List<UserRecipe>>() {
            @Override
            public void onResponse(Call<List<UserRecipe>> call, Response<List<UserRecipe>> response) {
                if (response.body() != null && response.body().stream().anyMatch(
                        userRecipe -> userRecipe.getRecipe().getId().equals(recipeId))) {

                    isFavourite = true;
                    favButton.setIcon(R.drawable.ic_heart);
                } else {
                    isFavourite = false;
                    favButton.setIcon(R.drawable.ic_heart_empty);
                }

                favButton.setOnMenuItemClickListener(getFavButtonListener(favButton, recipeId));
                favButton.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            }

            @Override
            public void onFailure(Call<List<UserRecipe>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private MenuItem.OnMenuItemClickListener getFavButtonListener(MenuItem favButton, Long recipeId) {
        return item -> {
            if (isFavourite) {
                foodbuddyAPI.removeRecipeFromFavourites(1L, recipeId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call1, Response<Void> response1) {
                        isFavourite = false;
                        favButton.setIcon(R.drawable.ic_heart_empty);
                    }

                    @Override
                    public void onFailure(Call<Void> call1, Throwable t) {
                        t.printStackTrace();
                    }
                });
            } else {
                foodbuddyAPI.addRecipeToFavourites(1L, recipeId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        isFavourite = true;
                        favButton.setIcon(R.drawable.ic_heart);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }

            return false;
        };
    }
}
