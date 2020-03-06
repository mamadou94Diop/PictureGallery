package com.mjob.picturegallery.injection

import com.mjob.picturegallery.ui.MainActivity
import com.mjob.picturegallery.ui.fragments.PictureDetailsFragment
import com.mjob.picturegallery.ui.fragments.PictureListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewsModule {
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesPictureListFragment(): PictureListFragment

    @ContributesAndroidInjector
    abstract fun contributesPictureDetailsFragment(): PictureDetailsFragment


}