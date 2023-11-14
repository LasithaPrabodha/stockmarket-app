package com.lasithaprabodha.stockmarketapp.data.mapper

import com.lasithaprabodha.stockmarketapp.data.local.CompanyListingEntity
import com.lasithaprabodha.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}


