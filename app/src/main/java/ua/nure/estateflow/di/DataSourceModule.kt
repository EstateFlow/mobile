package ua.nure.estateflow.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ua.nure.estateflow.data.datasource.ai.AiChatDataSource
import ua.nure.estateflow.data.datasource.ai.AiChatDataSourceImpl
import ua.nure.estateflow.data.datasource.auth.AuthDataSource
import ua.nure.estateflow.data.datasource.auth.AuthDataSourceImpl
import ua.nure.estateflow.data.datasource.db.DbDataSource
import ua.nure.estateflow.data.datasource.db.DbDataSourceImpl
import ua.nure.estateflow.data.datasource.profile.ProfileDataSource
import ua.nure.estateflow.data.datasource.profile.ProfileDataSourceImpl
import ua.nure.estateflow.data.datasource.property.PropertyDataSource
import ua.nure.estateflow.data.datasource.property.PropertyDataSourceImpl
import ua.nure.estateflow.data.datasource.token.TokenDataSource
import ua.nure.estateflow.data.datasource.token.TokenDataSourceImpl
import ua.nure.estateflow.data.datasource.wishList.WishListDataSource
import ua.nure.estateflow.data.datasource.wishList.WishListDataSourceImpl
import ua.nure.estateflow.data.remote.ai.AiChatApi
import ua.nure.estateflow.data.remote.auth.AuthApi
import ua.nure.estateflow.data.remote.property.PropertyApi
import ua.nure.estateflow.data.remote.wishlist.WishListApi
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
        tokenDataSource: TokenDataSource,
        profileDataSource: ProfileDataSource
    ): AuthDataSource = AuthDataSourceImpl(
        authApi = authApi,
        tokenDataSource = tokenDataSource,
        profileDataSource = profileDataSource,
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    fun providePropertyDataSource(
        propertyApi: PropertyApi,
        dbDataSource: DbDataSource,
        @DbDeliveryDispatcher dbDeliveryDispatcher: CloseableCoroutineDispatcher,
    ): PropertyDataSource = PropertyDataSourceImpl(
        propertyApi = propertyApi,
        dbDataSource = dbDataSource,
        dbDeliveryDispatcher = dbDeliveryDispatcher
    )

    @Provides
    @Singleton
    fun provideProfileDataSource(): ProfileDataSource = ProfileDataSourceImpl()

    @Provides
    @Singleton
    fun provideDbDataSource(
        @ApplicationContext context: Context,
        profileDataSource: ProfileDataSource
    ): DbDataSource = DbDataSourceImpl(
        context = context,
        profileDataSource = profileDataSource
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    fun provideWishListDataSource(
        wishListApi: WishListApi,
        dbDataSource: DbDataSource,
        @DbDeliveryDispatcher dbDeliveryDispatcher: CloseableCoroutineDispatcher,
    ): WishListDataSource = WishListDataSourceImpl(
        wishListApi = wishListApi,
        dbDataSource = dbDataSource,
        dbDeliveryDispatcher = dbDeliveryDispatcher
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    fun provideAiChatDataSource(
        aiChatApi: AiChatApi,
        dbDataSource: DbDataSource,
        @DbDeliveryDispatcher dbDeliveryDispatcher: CloseableCoroutineDispatcher,
    ): AiChatDataSource = AiChatDataSourceImpl(
        aiChatApi = aiChatApi,
        dbDataSource = dbDataSource,
        dbDeliveryDispatcher = dbDeliveryDispatcher
    )
}