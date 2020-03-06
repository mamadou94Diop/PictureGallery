package com.mjob.picturegallery.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mjob.picturegallery.R
import com.mjob.picturegallery.injection.viewmodel.ViewModelFactory
import com.mjob.picturegallery.repository.api.model.NetworkState
import com.mjob.picturegallery.repository.api.model.Picture
import com.mjob.picturegallery.ui.adapter.PictureListAdapter
import com.mjob.picturegallery.ui.viewmodels.PictureListViewModel
import com.mjob.picturegallery.utils.parentActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_picture_list.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class PictureListFragment : DaggerFragment(),
    OnPictureItemClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var adapter: PictureListAdapter

    lateinit var viewModel: PictureListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_picture_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViewModel()
        initializeView()
    }

    override fun openPicture(picture: Picture?) {
        picture?.let {
            val directions = PictureListFragmentDirections.actionPictureListFragmentToPictureDetailsFragment(picture)
            NavHostFragment.findNavController(this).navigate(directions)
        } ?: run {
            showErrorMessage(getString(R.string.error_open_file))
        }
    }

    private fun initializeView() {
        val toolbar = parentActivity().toolbar
        toolbar!!.title = getString(R.string.app_name)
        toolbar.navigationIcon = null

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(PictureListViewModel::class.java)

        getPictures()

        checkNetworkState()
    }

    private fun checkNetworkState() {
     /*   viewModel.networkState.observe(this, Observer<NetworkState> {
            onNetworkStateUpdated(it)
        })*/
    }

    private fun getPictures() {
        lifecycleScope.launch{
            viewModel.getPics().observe(viewLifecycleOwner, Observer<PagedList<Picture>> {
                adapter.submitList(it)
            })
        }

    }

    private fun onNetworkStateUpdated(networkState: NetworkState) {
        when (networkState.status) {
            NetworkState.Status.SUCCESS -> toggleProgressBarVisibility(false)
            NetworkState.Status.RUNNING -> toggleProgressBarVisibility(true)
            NetworkState.Status.FAILED -> showErrorMessage(getString(R.string.error_open_file))
        }
    }

    private fun toggleProgressBarVisibility(shouldBeVisible: Boolean) {
        if (shouldBeVisible)
            loadingProgressBar.visibility = View.VISIBLE
        else
            loadingProgressBar.visibility = View.GONE
    }

    private fun showErrorMessage(errorMessage: String) {
        Snackbar.make(view!!, errorMessage, Snackbar.LENGTH_LONG).show()
    }
}

interface OnPictureItemClickListener {
    fun openPicture(picture: Picture?)
}
