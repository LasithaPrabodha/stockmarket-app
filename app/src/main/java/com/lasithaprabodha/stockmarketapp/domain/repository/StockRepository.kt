package com.lasithaprabodha.stockmarketapp.domain.repository

import com.lasithaprabodha.stockmarketapp.domain.model.CompanyListing
import com.lasithaprabodha.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListing(
        fetchFromRemote:Boolean,
        query: String
    ) : Flow<Resource<List<CompanyListing>>>
}