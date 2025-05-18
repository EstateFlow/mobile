package ua.nure.estateflow.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.nure.estateflow.data.datasource.auth.AuthDataSource
import ua.nure.estateflow.data.datasource.auth.AuthDataSourceImpl
import ua.nure.estateflow.data.datasource.token.TokenDataSource
import ua.nure.estateflow.data.datasource.token.TokenDataSourceImpl
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
    ): AuthDataSource = AuthDataSourceImpl()

}