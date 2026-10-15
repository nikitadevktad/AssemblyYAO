package com.nikitadevktad.assemblyyao

import android.content.Context
import com.nikitadevktad.assemblyyao.data.local.AppDatabase
import com.nikitadevktad.assemblyyao.data.local.RoomNewsLocalDataSource
import com.nikitadevktad.assemblyyao.data.remote.RetrofitNewsRemoteDataSource
import com.nikitadevktad.assemblyyao.data.remote.retrofit.RetrofitClient
import com.nikitadevktad.assemblyyao.data.repository.NewsRepository
import com.nikitadevktad.assemblyyao.data.repository.OfflineFirstNewsRepository

class AppContainer(context: Context) {
    private val database = AppDatabase.create(context)
    private val api = RetrofitClient.createApi(enableBodyLogging = BuildConfig.DEBUG)

    val newsRepository: NewsRepository = OfflineFirstNewsRepository(
        remote = RetrofitNewsRemoteDataSource(api),
        local = RoomNewsLocalDataSource(database.newsDao()),
    )
}
