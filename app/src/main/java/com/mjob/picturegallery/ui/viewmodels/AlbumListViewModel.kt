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

    lateinit var factory : DataSource.Factory<Int,Int>

     suspend fun getAlbums() : LiveData<PagedList<Int>> {
        withContext(Dispatchers.IO) {
             factory = pictureInteractor.getAlbums()
        }
         val pagedListConfig =
             PagedList.Config.Builder()
                 .setEnablePlaceholders(false)
                 .setPageSize(20)
                 .setInitialLoadSizeHint(20)
                 .setPrefetchDistance(20)
                 .build()

       return  LivePagedListBuilder(factory,pagedListConfig).build()
    }



}