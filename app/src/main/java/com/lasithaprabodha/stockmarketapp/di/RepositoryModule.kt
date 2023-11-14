package com.lasithaprabodha.stockmarketapp.di

import com.lasithaprabodha.stockmarketapp.data.csv.CSVParser
import com.lasithaprabodha.stockmarketapp.data.csv.CompanyListingsParser
import com.lasithaprabodha.stockmarketapp.data.respository.StockRepositoryImpl
import com.lasithaprabodha.stockmarketapp.domain.model.CompanyListing
import com.lasithaprabodha.stockmarketapp.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}