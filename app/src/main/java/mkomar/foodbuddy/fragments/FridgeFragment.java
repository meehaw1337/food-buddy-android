package mkomar.foodbuddy.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mkomar.foodbuddy.R;
import mkomar.foodbuddy.adapters.ProductsAdapter;
import mkomar.foodbuddy.dialogs.UpdateQuantityDialog;
import mkomar.foodbuddy.model.UserProduct;
import mkomar.foodbuddy.services.interfaces.UserProductsService;
import mkomar.foodbuddy.services.web.UserProductsWebService;

public class FridgeFragment extends Fragment implements UpdateQuantityDialog.UpdateQuantityDialogListener {

    @BindView(R.id.user_products_recycler_view)
    RecyclerView userProductsRecyclerView;

    private UserProductsService userProductsService;
    private ProductsAdapter productsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fridge, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getActivity() != null) {
            this.getActivity().setTitle("Your fridge");
        }

        this.userProductsService = UserProductsWebService.getUserProductsWebProvider();
        new GetUserProductsTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onPositiveClick(UpdateQuantityDialog dialog, UserProduct userProduct) {
        Long updatedQuantity = Long.parseLong(dialog.getQuantityEditText().getText().toString());

        if (updatedQuantity > 0) {
            userProduct.setQuantity(updatedQuantity);
            productsAdapter.notifyDataSetChanged();
        } else {
            productsAdapter.removeProduct(userProduct);
        }

        new UpdateUsersProductQuantityTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                userProduct.getUserId(),
                userProduct.getId(),
                updatedQuantity);

        dialog.dismiss();
    }

    @Override
    public void onNegativeClick(UpdateQuantityDialog dialog, UserProduct userProduct) {
        dialog.dismiss();
    }

    private class GetUserProductsTask extends AsyncTask<Long, Void, List<UserProduct>> {

        @Override
        protected List<UserProduct> doInBackground(Long... longs) {
            return userProductsService.getUsersProducts(1L);
        }

        @Override
        protected void onPostExecute(List<UserProduct> userProducts) {
            if (userProducts != null) {
                productsAdapter = new ProductsAdapter(userProducts, getParentFragmentManager(),
                        FridgeFragment.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

                userProductsRecyclerView.setAdapter(productsAdapter);
                userProductsRecyclerView.setLayoutManager(layoutManager);

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                        userProductsRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
                userProductsRecyclerView.addItemDecoration(dividerItemDecoration);
            } else {
                Toast.makeText(getActivity(), "No data loaded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UpdateUsersProductQuantityTask extends AsyncTask <Long, Void, Void> {

        @Override
        protected Void doInBackground(Long... longs) {
            userProductsService.updateUsersProductQuantity(longs[0], longs[1], longs[2]);
            return null;
        }
    }
}
