package mkomar.foodbuddy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {

    private Long id;

    private String name;

    private String description;

    private Long calories;

    private String imageUrl;

    public Recipe() {
    }

    public Recipe(Long id, String name, String description, Long calories, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
