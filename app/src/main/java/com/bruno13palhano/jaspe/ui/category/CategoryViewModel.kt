package com.bruno13palhano.jaspe.ui.category

import androidx.lifecycle.ViewModel
import com.bruno13palhano.jaspe.R
import com.bruno13palhano.model.CategoryItem

class CategoryViewModel : ViewModel() {
    val categoryList = listOf(
        CategoryItem(
            "Baby",
            "category_baby",
            "ITEM_1",
            R.drawable.baby_icon
        ),

        CategoryItem(
            "Market",
            "category_market",
            "ITEM_2",
            R.drawable.market_icon
        ),

        CategoryItem(
            "Avon",
            "category_avon",
            "ITEM_3",
            R.drawable.avon_icon
        ),

        CategoryItem(
            "Natura",
            "category_natura",
            "ITEM_4",
            R.drawable.natura_icon
        ),

        CategoryItem(
            "Offers",
            "category_offers",
            "ITEM_5",
            R.drawable.offers_icon
        ),

        CategoryItem(
            "Highlights",
            "category_highlights",
            "ITEM_6",
            R.drawable.highlights_icon
        ),
    )
}