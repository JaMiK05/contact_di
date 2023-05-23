package uz.gita.contact_di.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.contact_di.data.source.local.sharedprevreference.SharedPrefRefrence
import uz.gita.contact_di.data.source.local.sharedprevreference.SharedPrev
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context) = SharedPrev(context)

}