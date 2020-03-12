package mkomar.foodbuddy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import mkomar.foodbuddy.R;
import mkomar.foodbuddy.adapters.RecipePagerAdapter;

public class RecipesFragment extends Fragment {

    @BindView(R.id.recipes_viewpager)
    ViewPager recipesViewPager;

    @BindView(R.id.recipe_tabs)
    TabLayout recipesTabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, view);

        recipesViewPager.setAdapter(new RecipePagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        recipesTabLayout.setupWithViewPager(recipesViewPager);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle("Recipes");
        }
    }
}
