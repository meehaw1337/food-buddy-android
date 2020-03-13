package mkomar.foodbuddy.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mkomar.foodbuddy.R;
import mkomar.foodbuddy.activities.RecipeDetailsActivity;
import mkomar.foodbuddy.model.Recipe;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    public static final int IMAGE_HEIGHT = 56;
    public static final int IMAGE_WIDTH = 100;

    public RecipeRecyclerViewAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        holder.recipeNameTextView.setText(recipe.getName());
        holder.recipeDescriptionTextView.setText(recipe.getDescription());
        holder.recipe = recipe;
        Picasso.get().load(recipe.getImageUrl()).resize(IMAGE_WIDTH, IMAGE_HEIGHT).into(holder.recipeImageView);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name_text_view)
        TextView recipeNameTextView;

        @BindView(R.id.recipe_description_text_view)
        TextView recipeDescriptionTextView;

        @BindView(R.id.recipe_image)
        ImageView recipeImageView;

        private Recipe recipe;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), RecipeDetailsActivity.class);

                intent.putExtra("name", recipe.getName());
                intent.putExtra("description", recipe.getDescription());
                intent.putExtra("id", recipe.getId());
                intent.putExtra("calories", recipe.getCalories());
                intent.putExtra("image_url", recipe.getImageUrl());

                itemView.getContext().startActivity(intent);
            });
        }
    }
}
