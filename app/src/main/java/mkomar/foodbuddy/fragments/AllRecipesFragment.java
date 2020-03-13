package mkomar.foodbuddy.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mkomar.foodbuddy.R;
import mkomar.foodbuddy.adapters.RecipeRecyclerViewAdapter;
import mkomar.foodbuddy.model.Recipe;
import mkomar.foodbuddy.services.FoodbuddyAPI;
import mkomar.foodbuddy.services.web.FoodbuddyAPIProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllRecipesFragment extends RecipesViewPagerFragment {

    @BindView(R.id.all_recipes_recycler_view)
    RecyclerView recipesRecyclerView;

    private RecipeRecyclerViewAdapter adapter;

    private DividerItemDecoration dividerItemDecoration;

    private FoodbuddyAPI foodbuddyAPI;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_recipes, container, false);

        ButterKnife.bind(this, view);

        foodbuddyAPI = FoodbuddyAPIProvider.get();

        if (savedInstanceState != null && adapter != null && dividerItemDecoration != null) {
            super.refreshRecyclerView(recipesRecyclerView, adapter, dividerItemDecoration);
        } else {
            foodbuddyAPI.getRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    adapter = new RecipeRecyclerViewAdapter(response.body());
                    dividerItemDecoration = new DividerItemDecoration(recipesRecyclerView.getContext(),
                            DividerItemDecoration.VERTICAL);

                    AllRecipesFragment.super.refreshRecyclerView(recipesRecyclerView, adapter, dividerItemDecoration);
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
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
