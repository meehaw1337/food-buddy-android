package mkomar.foodbuddy.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import mkomar.foodbuddy.R;
import mkomar.foodbuddy.model.UserProduct;

public class UpdateQuantityDialog extends DialogFragment {

    @BindView(R.id.quantity_edit_text)
    EditText quantityEditText;

    @BindView(R.id.unit_text_view)
    TextView unitTextView;

    public interface UpdateQuantityDialogListener {
        void onPositiveClick(UpdateQuantityDialog dialog, UserProduct userProduct);
        void onNegativeClick(UpdateQuantityDialog dialog, UserProduct userProduct);
    }

    private UpdateQuantityDialogListener listener;
    private UserProduct userProduct;

    public UpdateQuantityDialog(UpdateQuantityDialogListener listener, UserProduct userProduct) {
        super();
        this.listener = listener;
        this.userProduct = userProduct;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_change_product_quantity, null);

        ButterKnife.bind(this, view);
        quantityEditText.setText(userProduct.getQuantity().toString());
        unitTextView.setText(userProduct.getUnit());

        builder.setView(view)
                .setTitle("Update quantity of chosen product")
                .setPositiveButton("OK", (dialog, id) ->
                        listener.onPositiveClick(UpdateQuantityDialog.this, userProduct))
                .setNegativeButton("Cancel", (dialog, id) ->
                        listener.onNegativeClick(UpdateQuantityDialog.this, userProduct));
        return builder.create();
    }

    public EditText getQuantityEditText() {
        return quantityEditText;
    }

    public TextView getUnitTextView() {
        return unitTextView;
    }
}
