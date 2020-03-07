package com.mjob.picturegallery.injection

import androidx.lifecycle.ViewModel
import com.mjob.picturegallery.injection.viewmodel.ViewModelKey
import com.mjob.picturegallery.ui.viewmodels.AlbumListViewModel
import com.mjob.picturegallery.ui.viewmodels.PictureDetailsViewModel
import com.mjob.picturegallery.ui.viewmodels.PictureListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PictureListViewModel::class)
    internal abstract fun bindPictureListViewModel(pictureListViewModel: PictureListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PictureDetailsViewModel::class)
    internal abstract fun bindPictureDetailsViewModel(pictureDetailsViewModel: PictureDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlbumListViewModel::class)
    internal abstract fun bindAlbumListViewModel(albumListViewModel: AlbumListViewModel): ViewModel


}