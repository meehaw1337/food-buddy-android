package mkomar.foodbuddy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mkomar.foodbuddy.R;
import mkomar.foodbuddy.dialogs.UpdateQuantityDialog;
import mkomar.foodbuddy.model.UserProduct;
import mkomar.foodbuddy.services.interfaces.ProductImageService;
import mkomar.foodbuddy.services.local.ProductImageLocalService;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private List<UserProduct> products;
    private ProductImageService productImageService;
    private FragmentManager fragmentManager;
    private UpdateQuantityDialog.UpdateQuantityDialogListener listener;

    public ProductsAdapter(List<UserProduct> products, FragmentManager fragmentManager,
                           UpdateQuantityDialog.UpdateQuantityDialogListener listener) {
        this.products = products;
        productImageService = new ProductImageLocalService();
        this.fragmentManager = fragmentManager;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_product, parent, false);

        return new ProductViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        UserProduct userProduct = this.products.get(position);
        holder.userProduct = userProduct;

        String quantityText = userProduct.getQuantity() + " " + userProduct.getUnit();

        if (userProduct.getQuantity() > 1 && (
                "piece".equals(userProduct.getUnit()) ||
                "bottle".equals(userProduct.getUnit()))) {
            quantityText = quantityText + "s";
        }

        Integer productImageId = productImageService.getImageId(userProduct.getProduct().getName());

        holder.productNameTextView.setText(userProduct.getProduct().getName());
        holder.productQuantityTextView.setText(quantityText);
        holder.productCategoryTextView.setText(userProduct.getProduct().getCategory().getName());
        if (productImageId != null) {
            holder.productImageView.setImageResource(productImageId);
        }
    }


    public void removeProduct(UserProduct userProduct) {
        products.remove(userProduct);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.product_name_text_view)
        TextView productNameTextView;

        @BindView(R.id.product_quantity_text_view)
        TextView productQuantityTextView;

        @BindView(R.id.product_category_text_view)
        TextView productCategoryTextView;

        @BindView(R.id.product_image)
        ImageView productImageView;

        private UserProduct userProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(v -> {
                DialogFragment dialog = new UpdateQuantityDialog(listener, this.userProduct);
                dialog.show(fragmentManager, "quantity_dialog");
            });

            ButterKnife.bind(this, itemView);
        }
    }
}
