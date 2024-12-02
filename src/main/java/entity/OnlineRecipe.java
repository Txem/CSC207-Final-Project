package entity;

import java.util.List;

public class OnlineRecipe extends CommonRecipe {
    private String url;
    private String imageUrl;

    public OnlineRecipe(String recipeName, List<Ingredient> ingredients, String instruction, String username, String tag) {
        super(recipeName, ingredients, instruction, username, tag);
        this.imageUrl = "https://www.edamam.com/food-img/ecb/ecb3f5aaed96d0188c21b8369be07765.jpg";
        this.url = "https://www.edamam.com/";
    }

    public String getUrl() {
        return url;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
