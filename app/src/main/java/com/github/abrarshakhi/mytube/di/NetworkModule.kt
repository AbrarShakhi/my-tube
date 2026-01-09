package com.github.abrarshakhi.mytube.di

import com.github.abrarshakhi.mytube.data.remote.api.YoutubeApi
import com.github.abrarshakhi.mytube.data.remote.api.YoutubeRssApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val YT_API_URL = "https://www.googleapis.com/youtube/v3/"
    private const val YT_RSS_URL = "https://www.youtube.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    @Named("youtube_json")
    fun provideYoutubeJsonRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(YT_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("youtube_rss")
    fun provideYoutubeRssRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(YT_RSS_URL)
            .client(okHttpClient)
            .addConverterFactory(
                TikXmlConverterFactory.create(
                    TikXml.Builder().exceptionOnUnreadXml(false).build()
                )
            ).build()

    @Provides
    @Singleton
    fun provideYoutubeApi(@Named("youtube_json") retrofit: Retrofit): YoutubeApi =
        retrofit.create(YoutubeApi::class.java)

    @Provides
    @Singleton
    fun provideYoutubeRssApi(@Named("youtube_rss") retrofit: Retrofit): YoutubeRssApi =
        retrofit.create(YoutubeRssApi::class.java)
}
