package com.mjob.picturegallery.injection

import android.content.Context
import androidx.room.Room
import com.mjob.picturegallery.repository.api.PictureApiService
import com.mjob.picturegallery.repository.data.Database
import com.mjob.picturegallery.repository.api.impl.RemotePictureApiRepository
import com.mjob.picturegallery.BuildConfig
import com.mjob.picturegallery.PictureApplication

import com.mjob.picturegallery.repository.api.PictureApiRepository
import com.mjob.picturegallery.repository.data.PictureDataRepository
import com.mjob.picturegallery.repository.data.dao.PictureDao
import com.mjob.picturegallery.repository.data.impl.LocalPictureDataRepository
import com.mjob.picturegallery.utils.AppExecutors
import com.mjob.picturegallery.utils.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Singleton
    @Provides
    fun provideContext(application: PictureApplication): Context = application

    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providePictureApiService(retrofit: Retrofit): PictureApiService {
        return retrofit.create(PictureApiService::class.java)
    }

    @Provides
    fun provideExecutor() = AppExecutors()

    @Singleton
    @Provides
    fun provideCoroutineContextProvider() = CoroutineContextProvider()

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): Database {
        val databaseName = BuildConfig.DATABASE_NAME
        return Room.databaseBuilder(
            context.applicationContext, Database::class.java,
            databaseName
        ).build()
    }

    @Provides
    fun providePictureDao(database: Database): PictureDao {
        return database.pictureDao()
    }

    @Provides
    fun providePictureApiRepository(
        pictureApiService: PictureApiService,
        coroutineContextProvider: CoroutineContextProvider
    ): PictureApiRepository {
        return RemotePictureApiRepository(pictureApiService, coroutineContextProvider)

    }

    @Provides
    fun providePictureDataRepository(
        pictureDao: PictureDao,
        coroutineContextProvider: CoroutineContextProvider
    ): PictureDataRepository {
        return LocalPictureDataRepository(pictureDao, coroutineContextProvider)
    }
}