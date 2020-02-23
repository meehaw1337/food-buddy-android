package mkomar.foodbuddy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProduct {

    private Long id;

    private Long quantity;

    private String unit;

    private Long userId;

    private Product product;

    public UserProduct() {
    }

    public UserProduct(Long id, Long quantity, String unit, Long userId, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.unit = unit;
        this.userId = userId;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "UserProduct{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", userId=" + userId +
                ", product=" + product +
                '}';
    }
}
