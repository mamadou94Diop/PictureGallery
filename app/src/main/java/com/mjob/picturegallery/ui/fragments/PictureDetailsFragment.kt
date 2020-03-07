package com.mjob.picturegallery.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.mjob.picturegallery.R
import com.mjob.picturegallery.injection.viewmodel.ViewModelFactory
import com.mjob.picturegallery.ui.viewmodels.PictureDetailsViewModel
import com.mjob.picturegallery.utils.loadImageFromUrl
import com.mjob.picturegallery.utils.parentActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.picture_details_fragment.*
import javax.inject.Inject

class PictureDetailsFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: PictureDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.picture_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PictureDetailsViewModel::class.java)
        val toolbar = parentActivity().toolbar

        val picture = PictureDetailsFragmentArgs.fromBundle(arguments!!).picture

        toolbar!!.title = "Picture ${picture.id}"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        fullPicture.loadImageFromUrl(picture.url, {
            image_loading_progressbar.visibility = View.INVISIBLE
        }, {
            Snackbar.make(view, R.string.error_open_file, Snackbar.LENGTH_LONG).show()
        })
    }
}
