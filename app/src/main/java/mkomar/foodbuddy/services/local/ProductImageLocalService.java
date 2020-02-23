package mkomar.foodbuddy.services.local;

import mkomar.foodbuddy.R;
import mkomar.foodbuddy.services.interfaces.ProductImageService;

public class ProductImageLocalService implements ProductImageService {
    @Override
    public Integer getImageId(String productName) {
        switch (productName) {
            case "Tomato":
                return R.drawable.tomato;
            case "Banana":
                return R.drawable.banana;
            case "Coca Cola":
                return R.drawable.cola;
            case "Chicken wing":
                return R.drawable.fried_chicken;
            case "Ketchup":
                return R.drawable.ketchup;
            default:
                return null;
        }
    }
}
