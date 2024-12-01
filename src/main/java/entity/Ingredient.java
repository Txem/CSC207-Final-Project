package entity;

public class Ingredient {
    private String ingredientName;
    private String quantity;

    public Ingredient(String name, String quantity) {
        this.ingredientName = name;
        this.quantity = quantity;
    }

    public String getIngredientName() {
        return this.ingredientName;
    }

    public String getQuantity() {
        return this.quantity;
    }

    @Override
    public String toString() {
        return "Ingredient{" + "ingredientName= " + ingredientName + ", quantity= " + quantity + '}';
    }
}
