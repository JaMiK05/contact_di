package uz.gita.contact_di.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.contact_di.data.source.local.sharedprevreference.SharedPrefRefrence
import uz.gita.contact_di.data.source.local.sharedprevreference.SharedPrev
import uz.gita.contact_di.domain.repositories.AuthRepositories
import uz.gita.contact_di.domain.repositories.ContactRepositories
import uz.gita.contact_di.domain.repositories.impl.AuthRepositoryImpl
import uz.gita.contact_di.domain.repositories.impl.ContactRepositoryimpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepositories

    @[Binds Singleton]
    fun bindContactRepository(impl: ContactRepositoryimpl): ContactRepositories

    @[Binds Singleton]
    fun bindShared(shared: SharedPrev): SharedPrefRefrence


}