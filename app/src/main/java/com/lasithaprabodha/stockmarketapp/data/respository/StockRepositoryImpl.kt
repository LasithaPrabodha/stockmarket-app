package com.lasithaprabodha.stockmarketapp.data.respository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.lasithaprabodha.stockmarketapp.data.csv.CSVParser
import com.lasithaprabodha.stockmarketapp.data.local.StockDatabase
import com.lasithaprabodha.stockmarketapp.data.mapper.toCompanyListing
import com.lasithaprabodha.stockmarketapp.data.mapper.toCompanyListingEntity
import com.lasithaprabodha.stockmarketapp.data.remote.dto.StockApi
import com.lasithaprabodha.stockmarketapp.domain.model.CompanyListing
import com.lasithaprabodha.stockmarketapp.domain.repository.StockRepository
import com.lasithaprabodha.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase,
    val companyListingsParser: CSVParser<CompanyListing>
) : StockRepository {
    private val dao = db.dao

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getCompanyListing(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))

            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't Load Data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't Load Data"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListing()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )

                emit(Resource.Success(
                    data = dao
                        .searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

}