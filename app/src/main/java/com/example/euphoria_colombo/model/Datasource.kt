package com.example.euphoria_colombo.model

import com.example.euphoria_colombo.R

class Datasource {
    fun Topselling(): List<Picture> {
        return listOf(
            Picture(
                titleResId = R.string.chain1_title,
                priceResId = R.string.chain1_price,
                imageResId = R.drawable.chain1
            ),
            Picture(
                titleResId = R.string.earring1_title,
                priceResId = R.string.earring1_price,
                imageResId = R.drawable.earring1
            ),
            Picture(
                titleResId = R.string.earring2_title,
                priceResId = R.string.earring2_price,
                imageResId = R.drawable.earring2
            ),
            Picture(
                titleResId = R.string.ring1_title,
                priceResId = R.string.ring1_price,
                imageResId = R.drawable.ring1
            ),

            // Add more chain pictures here
        )
    }

    fun loadChains(): List<Picture> {
        return listOf(
            Picture(
                titleResId = R.string.chain1_title,
                priceResId = R.string.chain1_price,
                imageResId = R.drawable.chain1
            ),
            Picture(
                titleResId = R.string.chain2_title,
                priceResId = R.string.chain2_price,
                imageResId = R.drawable.chain2
            ),
            Picture(
                titleResId = R.string.chain3_title,
                priceResId = R.string.chain3_price,
                imageResId = R.drawable.chain3
            ),
            Picture(
                titleResId = R.string.chain4_title,
                priceResId = R.string.chain4_price,
                imageResId = R.drawable.chain4
            ),
            Picture(
                titleResId = R.string.chain5_title,
                priceResId = R.string.chain5_price,
                imageResId = R.drawable.chain5
            ),
            Picture(
                titleResId = R.string.chain6_title,
                priceResId = R.string.chain6_price,
                imageResId = R.drawable.chain7
            ),
            Picture(
                titleResId = R.string.chain7_title,
                priceResId = R.string.chain7_price,
                imageResId = R.drawable.chain6
            ),
            Picture(
                titleResId = R.string.chain8_title,
                priceResId = R.string.chain8_price,
                imageResId = R.drawable.chain8
            ),
            Picture(
                titleResId = R.string.chain9_title,
                priceResId = R.string.chain9_price,
                imageResId = R.drawable.chain9
            ),
            Picture(
                titleResId = R.string.chain10_title,
                priceResId = R.string.chain10_price,
                imageResId = R.drawable.chain10
            ),
            Picture(
                titleResId = R.string.chain11_title,
                priceResId = R.string.chain11_price,
                imageResId = R.drawable.chain11
            ),
            Picture(
                titleResId = R.string.chain12_title,
                priceResId = R.string.chain12_price,
                imageResId = R.drawable.chain12
            ),

        )
    }

    fun loadEarrings(): List<Picture> {
        return listOf(
            Picture(
                titleResId = R.string.earring1_title,
                priceResId = R.string.earring1_price,
                imageResId = R.drawable.earring1
            ),
            Picture(
                titleResId = R.string.earring2_title,
                priceResId = R.string.earring2_price,
                imageResId = R.drawable.earring2
            ),
            Picture(
                titleResId = R.string.earring3_title,
                priceResId = R.string.earring3_price,
                imageResId = R.drawable.earring3
            ),
            Picture(
                titleResId = R.string.earring4_title,
                priceResId = R.string.earring4_price,
                imageResId = R.drawable.earring4
            ),
            Picture(
                titleResId = R.string.earring5_title,
                priceResId = R.string.earring5_price,
                imageResId = R.drawable.earring5
            ),
            Picture(
                titleResId = R.string.earring6_title,
                priceResId = R.string.earring6_price,
                imageResId = R.drawable.earring6
            ),
            Picture(
                titleResId = R.string.earring7_title,
                priceResId = R.string.earring7_price,
                imageResId = R.drawable.earring7
            ),
            Picture(
                titleResId = R.string.earring8_title,
                priceResId = R.string.earring8_price,
                imageResId = R.drawable.earring8
            ),
            // Add more earring pictures here
        )
    }

    fun loadRings(): List<Picture> {
        return listOf(
            Picture(
                titleResId = R.string.ring1_title,
                priceResId = R.string.ring1_price,
                imageResId = R.drawable.ring1
            ),
            Picture(
                titleResId = R.string.ring2_title,
                priceResId = R.string.ring2_price,
                imageResId = R.drawable.ring2
            ),
            Picture(
                titleResId = R.string.ring3_title,
                priceResId = R.string.ring3_price,
                imageResId = R.drawable.ring3
            ),
            Picture(
                titleResId = R.string.ring4_title,
                priceResId = R.string.ring4_price,
                imageResId = R.drawable.ring4
            ),
            Picture(
                titleResId = R.string.ring5_title,
                priceResId = R.string.ring5_price,
                imageResId = R.drawable.ring5
            ),
            Picture(
                titleResId = R.string.ring6_title,
                priceResId = R.string.ring6_price,
                imageResId = R.drawable.ring6
            ),
            Picture(
                titleResId = R.string.ring7_title,
                priceResId = R.string.ring7_price,
                imageResId = R.drawable.ring7
            ),
            Picture(
                titleResId = R.string.ring8_title,
                priceResId = R.string.ring8_price,
                imageResId = R.drawable.ring8
            ),
            // Add more ring pictures here
        )
    }
}
