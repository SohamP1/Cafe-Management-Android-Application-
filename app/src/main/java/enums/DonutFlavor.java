package enums;

public enum DonutFlavor {
    glazed("Glazed", DonutType.YEAST),
    chocolate("Chocolate", DonutType.YEAST),
    strawberry("Strawberry", DonutType.YEAST),
    blueberry("Blueberry", DonutType.YEAST),
    maple("Maple", DonutType.YEAST),
    cinnamon_sugar("Cinnamon Sugar", DonutType.YEAST),

    powdered("Powdered", DonutType.DONUT_HOLE),
    cinnamon("Cinnamon", DonutType.DONUT_HOLE),
    sugar("Sugar", DonutType.DONUT_HOLE),

    vanilla_glaze("Vanilla Glaze", DonutType.CAKE),
    lemon("Lemon", DonutType.CAKE),
    red_velvet("Red Velvet", DonutType.CAKE);

    private final String flavorName;
    private final DonutType associatedType;

    DonutFlavor(String flavorName, DonutType associatedType) {
        this.flavorName = flavorName;
        this.associatedType = associatedType;
    }

    public String getFlavorName() {
        return flavorName;
    }

    public DonutType getAssociatedType() {
        return associatedType;
    }}
