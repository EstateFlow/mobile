package ua.nure.estateflow.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.nure.estateflow.data.datasource.auth.AuthDataSource
import ua.nure.estateflow.data.datasource.auth.AuthDataSourceImpl
import ua.nure.estateflow.data.datasource.property.PropertyDataSource
import ua.nure.estateflow.data.datasource.property.PropertyDataSourceImpl
import ua.nure.estateflow.data.datasource.token.TokenDataSource
import ua.nure.estateflow.data.datasource.token.TokenDataSourceImpl
import ua.nure.estateflow.data.remote.auth.AuthApi
import ua.nure.estateflow.data.remote.property.PropertyApi
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideTokenDataSource(
        sharedPreferences: SharedPreferences
    ) : TokenDataSource = TokenDataSourceImpl(
        sharedPreferences = sharedPreferences
    )

    @Provides
    fun provideAuthDataSource(
        authApi: AuthApi,
        tokenDataSource: TokenDataSource
    ): AuthDataSource = AuthDataSourceImpl(
        authApi = authApi,
        tokenDataSource = tokenDataSource
    )

    @Provides
    fun providePropertyDataSource(
        propertyApi: PropertyApi
    ): PropertyDataSource = PropertyDataSourceImpl(
        propertyApi = propertyApi
    )
}