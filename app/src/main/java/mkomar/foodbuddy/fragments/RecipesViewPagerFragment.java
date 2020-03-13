package mkomar.foodbuddy.fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mkomar.foodbuddy.adapters.RecipeRecyclerViewAdapter;

public abstract class RecipesViewPagerFragment extends Fragment {

    protected void refreshRecyclerView(RecyclerView recipesRecyclerView, RecipeRecyclerViewAdapter adapter,
                                     DividerItemDecoration dividerItemDecoration) {
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipesRecyclerView.setAdapter(adapter);
        recipesRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}
