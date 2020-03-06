package com.mjob.picturegallery.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class PictureDetailsViewModel @Inject constructor() : ViewModel() {
    lateinit var data: MutableLiveData<String>
}
