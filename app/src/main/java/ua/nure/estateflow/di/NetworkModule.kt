package ua.nure.estateflow.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import ua.nure.estateflow.data.remote.auth.AuthApi
import ua.nure.estateflow.data.remote.property.PropertyApi
import ua.nure.estateflow.data.remote.wishlist.WishListApi

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    fun providePropertyApi(retrofit: Retrofit): PropertyApi = retrofit.create(PropertyApi::class.java)

    @Provides
    fun provideWishListApi(retrofit: Retrofit): WishListApi = retrofit.create(WishListApi::class.java)
}