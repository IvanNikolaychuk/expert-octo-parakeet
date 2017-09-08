package com.stocks.livermor.entity;

public enum Signal {
    /**
     *  \
     *   \      /\
     *    \   /   \
     *     \/      \      /\
     *              \   /   \/
     *               \/    COME CLOSE, BUT JUMP FROM IT
     *           DOWN_PP
     */
    DOWN_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_DOWN_TREND_POINT,

    /**                     UP_PP
     *                       /\    COME CLOSE, BUT JUMP FROM IT
     *                     /   \   /\
     *                   /      \/
     *         /\      /
     *       /   \   /
     *     /      \/
     *   /
     */
    UPPER_TREND_IS_OVER_BECAUSE_PRICE_JUMPS_FROM_NEAR_UPPER_TREND_POINT,

    // нисходящий тренд пробит после реакции, но незначительно

    /**
     *  \
     *   \      /\
     *    \   /   \
     *     \/      \      /\
     *              \   /   \
     *               \/      \
     *           DOWN_PP      \/
     *                      DOWN PP BROKEN, BUT A BIT
     */
    DOWN_TREND_IS_OVER_BECAUSE_LAST_DOWN_PIVOT_POINT_IS_BROKEN_WEAK,

    /**
     *                             UP PP BROKEN, BUT A BIT
     *                      UP_PP      /\
     *                       /\      /
     *                     /   \   /
     *                   /      \/
     *         /\      /
     *       /   \   /
     *     /      \/
     *   /
     */
    UPPER_TREND_IS_OVER_BECAUSE_LAST_UPPER_PIVOT_POINT_IS_BROKEN_WEAK
}
