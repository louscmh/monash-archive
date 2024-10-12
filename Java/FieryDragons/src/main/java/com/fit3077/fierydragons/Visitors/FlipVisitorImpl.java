package com.fit3077.fierydragons.Visitors;

import com.fit3077.fierydragons.DragonCards.*;

/**
 * A class that represents a concrete Flip Visitor class that
 * implements several versions of the same flipping algorithm for
 * each animal variant of a Dragon Card
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class FlipVisitorImpl implements FlipVisitor{
    /**
     * Method for flipping a Baby Dragon Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param babyDragonDragonCard The Baby Dragon Dragon Card to be flipped
     */
    @Override
    public void flipBabyDragonDragonCard(BabyDragonDragonCard babyDragonDragonCard) {
        babyDragonDragonCard.setFlipped(!babyDragonDragonCard.isFlipped());
    }

    /**
     * Method for flipping a Bat Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param batDragonCard The Bat Dragon Card to be flipped
     */
    @Override
    public void flipBatDragonCard(BatDragonCard batDragonCard) {
        batDragonCard.setFlipped(!batDragonCard.isFlipped());
    }

    /**
     * Method for flipping a Salamander Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param salamanderDragonCard The Salamander Dragon Card to be flipped
     */
    @Override
    public void flipSalamanderDragonCard(SalamanderDragonCard salamanderDragonCard) {
        salamanderDragonCard.setFlipped(!salamanderDragonCard.isFlipped());
    }

    /**
     * Method for flipping a Spider Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param spiderDragonCard The Spider Dragon Card to be flipped
     */
    @Override
    public void flipSpiderDragonCard(SpiderDragonCard spiderDragonCard) {
        spiderDragonCard.setFlipped(!spiderDragonCard.isFlipped());
    }

    /**
     * Method for flipping a Dragon Pirate Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param dragonPirateDragonCard The Dragon Pirate Dragon Card to be flipped
     */
    @Override
    public void flipDragonPirateDragonCard(DragonPirateDragonCard dragonPirateDragonCard) {
        dragonPirateDragonCard.setFlipped(!dragonPirateDragonCard.isFlipped());
    }

    /**
     * Method for flipping a Swap Dragon Card
     *
     * Created by:
     * @author Louis Meng Hoe Chow
     *
     * @param swapDragonCard The Dragon Pirate Dragon Card to be flipped
     */
    @Override
    public void flipSwapDragonCard(SwapDragonCard swapDragonCard) {
        swapDragonCard.setFlipped(!swapDragonCard.isFlipped());
    }
}
