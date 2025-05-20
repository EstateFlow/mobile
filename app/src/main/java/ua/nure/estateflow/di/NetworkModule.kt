package ua.nure.estateflow.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import ua.nure.estateflow.data.remote.auth.AuthApi
import ua.nure.estateflow.data.remote.property.PropertyApi

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    fun providePropertyApi(retrofit: Retrofit): PropertyApi = retrofit.create(PropertyApi::class.java)
}