package game.elements;

/**
 * Modified by:
 * @author Louis Meng Hoe Chow
 */
public enum Element {
    WATER("Water"),
    FIRE("Fire"),
    GRASS("Grass"),
    DRAGON("Dragon");

    private final String label;

    Element(String label){
        this.label = label;
    }

    /**
     *
     * @return the label text
     */
    @Override
    public String toString() {
        return label;
    }
}
