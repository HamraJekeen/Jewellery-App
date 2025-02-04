package com.example.euphoria_colombo.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Picture(

    @StringRes val titleResId: Int,
    @StringRes val priceResId: Int,
    @DrawableRes val imageResId: Int
)
