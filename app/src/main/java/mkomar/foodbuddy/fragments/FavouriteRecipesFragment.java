package mkomar.foodbuddy.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import mkomar.foodbuddy.R;
import mkomar.foodbuddy.adapters.RecipeRecyclerViewAdapter;
import mkomar.foodbuddy.model.UserRecipe;
import mkomar.foodbuddy.services.FoodbuddyAPI;
import mkomar.foodbuddy.services.web.FoodbuddyAPIProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteRecipesFragment extends RecipesViewPagerFragment {

    @BindView(R.id.favourite_recipes_recycler_view)
    RecyclerView recipesRecyclerView;

    private RecipeRecyclerViewAdapter adapter;

    private DividerItemDecoration dividerItemDecoration;

    private FoodbuddyAPI foodbuddyAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite_recipes, container, false);

        ButterKnife.bind(this, view);

        foodbuddyAPI = FoodbuddyAPIProvider.get();

        if (savedInstanceState != null && adapter != null && dividerItemDecoration != null) {
            super.refreshRecyclerView(recipesRecyclerView, adapter, dividerItemDecoration);
        } else {
            // TODO remove hardcoded user ID
            foodbuddyAPI.getUsersFavouriteRecipes(1L).enqueue(new Callback<List<UserRecipe>>() {
                @Override
                public void onResponse(Call<List<UserRecipe>> call, Response<List<UserRecipe>> response) {
                    if (response.body() != null) {
                        adapter = new RecipeRecyclerViewAdapter(response.body().stream().map(UserRecipe::getRecipe)
                                .collect(Collectors.toList()));

                        dividerItemDecoration = new DividerItemDecoration(recipesRecyclerView.getContext(),
                                DividerItemDecoration.VERTICAL);

                        FavouriteRecipesFragment.super.refreshRecyclerView(recipesRecyclerView, adapter, dividerItemDecoration);
                    }
                }

                @Override
                public void onFailure(Call<List<UserRecipe>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
}
