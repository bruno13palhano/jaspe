package com.bruno13palhano.jaspe.ui.home

import com.bruno13palhano.model.Banner

data class HomeBannersUiState(
    val mainBanner: Banner = Banner(),
    val amazonBanner: Banner = Banner(),
    val naturaBanner: Banner = Banner(),
    val avonBanner: Banner = Banner()
)
