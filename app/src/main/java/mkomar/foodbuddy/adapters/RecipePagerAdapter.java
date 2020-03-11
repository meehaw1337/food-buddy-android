package mkomar.foodbuddy.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import mkomar.foodbuddy.fragments.AllRecipesFragment;
import mkomar.foodbuddy.fragments.AvailableRecipesFragment;
import mkomar.foodbuddy.fragments.FavouriteRecipesFragment;

public class RecipePagerAdapter extends FragmentPagerAdapter {

    public RecipePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AvailableRecipesFragment();
            case 1:
                return new AllRecipesFragment();
            case 2:
                return new FavouriteRecipesFragment();
        }

        return new AvailableRecipesFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Available";
            case 1:
                return "All";
            case 2:
                return "Favourite";
        }

        return super.getPageTitle(position);
    }
}
