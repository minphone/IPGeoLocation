package com.minphone.ipgeolocation.di

import com.minphone.ipgeolocation.data.repository.restClient.IpGeolocationRestClient
import com.minphone.ipgeolocation.data.repository.restClient.IpGeolocationRestClientImpl
import com.minphone.ipgeolocation.data.repository.IpGeolocationRepository
import com.minphone.ipgeolocation.data.repository.IpGeolocationRepositoryImpl
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
    abstract fun bindIpGeolocationRestClient(
        ipGeolocationRestClientImpl: IpGeolocationRestClientImpl
    ): IpGeolocationRestClient

    @Binds
    @Singleton
    abstract fun bindIpGeolocationRepository(
        ipGeolocationRepositoryImpl: IpGeolocationRepositoryImpl
    ): IpGeolocationRepository
}
