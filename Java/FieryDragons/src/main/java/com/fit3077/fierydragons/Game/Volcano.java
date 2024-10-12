package com.fit3077.fierydragons.Game;

import com.fit3077.fierydragons.DragonCards.DragonCard;
import com.fit3077.fierydragons.VolcanoCards.VolcanoCard;

import java.util.List;

/**
 * A class that represents the Volcano within Fiery Dragons
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class Volcano {
    private List<VolcanoCard> volcanoCards;
    private List<DragonCard> dragonCards;

    public List<VolcanoCard> getVolcanoCards() {
        return volcanoCards;
    }

    public void setVolcanoCards(List<VolcanoCard> volcanoCards) {
        this.volcanoCards = volcanoCards;
    }

    public List<DragonCard> getDragonCards() {
        return dragonCards;
    }

    public void setDragonCards(List<DragonCard> dragonCards) {
        this.dragonCards = dragonCards;
    }

    /**
     * Constructor
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param volcanoCards a List of Volcano Cards that compose the outer ring of the Volcano
     * @param dragonCards a List of Dragon Cards that compose the centre of the Volcano
     */
    public Volcano(List<VolcanoCard> volcanoCards, List<DragonCard> dragonCards) {
        this.volcanoCards = volcanoCards;
        this.dragonCards = dragonCards;
    }
}
