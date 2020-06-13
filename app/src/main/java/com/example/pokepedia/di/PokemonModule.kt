package com.example.pokepedia.di

import com.example.pokepedia.api.PokemonService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class PokemonModule {

    @Singleton
    @Provides
    fun provideStarWarService(retrofit: Retrofit): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }
}