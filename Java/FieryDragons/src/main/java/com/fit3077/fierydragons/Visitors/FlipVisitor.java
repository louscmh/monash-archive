package com.fit3077.fierydragons.Visitors;

import com.fit3077.fierydragons.DragonCards.*;

/**
 * An interface for representing a Flip Visitor as part of the Visitor design pattern.
 *
 * Created by:
 * @author Kerk Han Chin
 */
public interface FlipVisitor {
    /**
     * Method for flipping a Baby Dragon Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param babyDragonDragonCard The Baby Dragon Dragon Card to be flipped
     */
    void flipBabyDragonDragonCard(BabyDragonDragonCard babyDragonDragonCard);

    /**
     * Method for flipping a Bat Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param batDragonCard The Bat Dragon Card to be flipped
     */
    void flipBatDragonCard(BatDragonCard batDragonCard);

    /**
     * Method for flipping a Salamander Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param salamanderDragonCard The Salamander Dragon Card to be flipped
     */
    void flipSalamanderDragonCard(SalamanderDragonCard salamanderDragonCard);

    /**
     * Method for flipping a Spider Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param spiderDragonCard The Spider Dragon Card to be flipped
     */
    void flipSpiderDragonCard(SpiderDragonCard spiderDragonCard);

    /**
     * Method for flipping a Dragon Pirate Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param dragonPirateDragonCard The Dragon Pirate Dragon Card to be flipped
     */
    void flipDragonPirateDragonCard(DragonPirateDragonCard dragonPirateDragonCard);

    /**
     * Method for flipping a Dragon Pirate Dragon Card
     *
     * Created by:
     * @author Louis Meng Hoe Chow
     *
     * @param swapDragonCard The Swap Dragon Card to be flipped
     */
    void flipSwapDragonCard(SwapDragonCard swapDragonCard);
}
