package com.mjob.picturegallery.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mjob.picturegallery.interactor.PictureInteractor
import com.mjob.picturegallery.repository.api.model.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumListViewModel @Inject constructor(private val pictureInteractor: PictureInteractor) :
    ViewModel() {


    suspend fun getAlbums(): LiveData<PagedList<Int>>? {
        var factory: DataSource.Factory<Int, Int>? = null
        var albums: LiveData<PagedList<Int>>? = null

        withContext(Dispatchers.IO) {
            factory = pictureInteractor.getAlbums()
        }

        if (factory != null) {
            val pagedListConfig =
                PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setPageSize(20)
                    .setInitialLoadSizeHint(20)
                    .setPrefetchDistance(20)
                    .build()

            albums = LivePagedListBuilder(factory!!, pagedListConfig).build()
        }

        return albums
    }


}