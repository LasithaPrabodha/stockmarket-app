package com.lasithaprabodha.stockmarketapp.presentation.company_listing

import com.lasithaprabodha.stockmarketapp.domain.model.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",

)
