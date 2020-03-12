package mkomar.foodbuddy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mkomar.foodbuddy.R;
import mkomar.foodbuddy.activities.AddProductsActivity;
import mkomar.foodbuddy.adapters.ProductsAdapter;
import mkomar.foodbuddy.dialogs.UpdateQuantityDialog;
import mkomar.foodbuddy.model.UserProduct;
import mkomar.foodbuddy.services.FoodbuddyAPI;
import mkomar.foodbuddy.services.web.FoodbuddyAPIProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FridgeFragment extends Fragment implements UpdateQuantityDialog.UpdateQuantityDialogListener {

    @BindView(R.id.user_products_recycler_view)
    RecyclerView userProductsRecyclerView;

    @BindView(R.id.add_product_button)
    FloatingActionButton addProductButton;

    private FoodbuddyAPI foodbuddyAPI;

    private ProductsAdapter productsAdapter;
    private DividerItemDecoration dividerItemDecoration;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fridge, container, false);
        ButterKnife.bind(this, view);

        if (getActivity() != null) {
            getActivity().setTitle("Your fridge");
        }

        addProductButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddProductsActivity.class);

            startActivity(intent);
        });

        if (savedInstanceState != null && productsAdapter != null && dividerItemDecoration != null) {
            userProductsRecyclerView.setAdapter(productsAdapter);
            userProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            userProductsRecyclerView.addItemDecoration(dividerItemDecoration);
        } else {
            foodbuddyAPI = FoodbuddyAPIProvider.get();

            // TODO remove hardcoded user id
            foodbuddyAPI.getUsersProducts(1L).enqueue(new Callback<List<UserProduct>>() {
                @Override
                public void onResponse(Call<List<UserProduct>> call, Response<List<UserProduct>> response) {
                    List<UserProduct> userProducts = response.body();

                    if (userProducts != null) {
                        productsAdapter = new ProductsAdapter(userProducts, getParentFragmentManager(),
                                FridgeFragment.this);

                        userProductsRecyclerView.setAdapter(productsAdapter);
                        userProductsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                        dividerItemDecoration = new DividerItemDecoration(userProductsRecyclerView.getContext(),
                                DividerItemDecoration.VERTICAL);
                        userProductsRecyclerView.addItemDecoration(dividerItemDecoration);
                    }
                }

                @Override
                public void onFailure(Call<List<UserProduct>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onPositiveClick(UpdateQuantityDialog dialog, UserProduct userProduct) {
        Long updatedQuantity = Long.parseLong(dialog.getQuantityEditText().getText().toString());

        foodbuddyAPI.updateUsersProductQuantity(userProduct.getUserId(), userProduct.getProduct().getId(), updatedQuantity).enqueue(
                new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (updatedQuantity > 0) {
                            userProduct.setQuantity(updatedQuantity);
                            productsAdapter.notifyDataSetChanged();
                        } else {
                            productsAdapter.removeProduct(userProduct);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                }
        );

        dialog.dismiss();
    }

    @Override
    public void onNegativeClick(UpdateQuantityDialog dialog, UserProduct userProduct) {
        dialog.dismiss();
    }
}
