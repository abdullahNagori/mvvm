package com.uhfsolutions.abl.model.marketingCollateral

data class MarketingCollateralResponse (
    val section: String,
    val `data`: List<MarketingCollateralItem>
)