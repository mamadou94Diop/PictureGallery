package com.mjob.picturegallery.injection

import com.mjob.picturegallery.PictureApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        ViewsModule::class,
        ViewModelModule::class,
        ApplicationModule::class]
)
interface ApplicationComponent : AndroidInjector<PictureApplication> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<PictureApplication>
}