package mkomar.foodbuddy.services.interfaces;

import java.util.List;

import mkomar.foodbuddy.model.UserProduct;

public interface UserProductsService {

    List<UserProduct> getUsersProducts(Long userId);
    void updateUsersProductQuantity(Long userId, Long userProductId, Long updatedQuantity);
}
