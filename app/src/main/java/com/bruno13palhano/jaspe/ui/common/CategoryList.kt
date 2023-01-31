package com.bruno13palhano.jaspe.ui.common

import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.CategoryItem
import com.bruno13palhano.model.Route

fun getCategoryList(): List<CategoryItem> {
    return listOf(
        CategoryItem(
            "Baby",
            Route.BABY.route,
            "ITEM_1",
            R.drawable.baby_icon
        ),

        CategoryItem(
            "Market",
            Route.MARKET.route,
            "ITEM_2",
            R.drawable.market_icon
        ),

        CategoryItem(
            "Avon",
            Route.AVON.route,
            "ITEM_3",
            R.drawable.avon_icon
        ),

        CategoryItem(
            "Natura",
            Route.NATURA.route,
            "ITEM_4",
            R.drawable.natura_icon
        ),

        CategoryItem(
            "Offers",
            Route.OFFERS.route,
            "ITEM_5",
            R.drawable.offers_icon
        ),

        CategoryItem(
            "Recent",
            Route.LAST_SEEN.route,
            "ITEM_6",
            R.drawable.last_seen_icon
        )
    )
}