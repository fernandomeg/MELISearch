package com.gallardf.melisearch.di

import com.gallardf.melisearch.domain.repository.MercadoLibreRepository
import com.gallardf.melisearch.data.repository_impl.MercadoLibreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun mercadoLibreRepository(impl: MercadoLibreRepositoryImpl): MercadoLibreRepository
}