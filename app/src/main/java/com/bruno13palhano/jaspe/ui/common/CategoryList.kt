package com.bruno13palhano.jaspe.ui.common

import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.CategoryItem
import com.bruno13palhano.model.CategoryRoute

fun getCategoryList(): List<CategoryItem> {
    return listOf(
        CategoryItem(
            "Baby",
            CategoryRoute.BABY.route,
            "ITEM_1",
            R.drawable.baby_icon
        ),

        CategoryItem(
            "Market",
            CategoryRoute.MARKET.route,
            "ITEM_2",
            R.drawable.market_icon
        ),

        CategoryItem(
            "Avon",
            CategoryRoute.AVON.route,
            "ITEM_3",
            R.drawable.avon_icon
        ),

        CategoryItem(
            "Natura",
            CategoryRoute.NATURA.route,
            "ITEM_4",
            R.drawable.natura_icon
        ),

        CategoryItem(
            "Offers",
            CategoryRoute.OFFERS.route,
            "ITEM_5",
            R.drawable.offers_icon
        ),

        CategoryItem(
            "Recent",
            CategoryRoute.LAST_SEEN.route,
            "ITEM_6",
            R.drawable.last_seen_icon
        )
    )
}