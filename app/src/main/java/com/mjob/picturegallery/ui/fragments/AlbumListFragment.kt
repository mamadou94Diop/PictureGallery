package com.mjob.picturegallery.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mjob.picturegallery.R
import com.mjob.picturegallery.injection.viewmodel.ViewModelFactory
import com.mjob.picturegallery.ui.adapter.AlbumListAdapter
import com.mjob.picturegallery.ui.viewmodels.AlbumListViewModel
import com.mjob.picturegallery.utils.parentActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_album_list.*
import kotlinx.android.synthetic.main.fragment_picture_list.recyclerView
import kotlinx.coroutines.launch
import javax.inject.Inject


class AlbumListFragment : DaggerFragment(), OnAlbumEventListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var adapter: AlbumListAdapter

    lateinit var viewModel: AlbumListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewModel()
        initializeView()

        adapter.listener = this
        recyclerView.layoutManager = GridLayoutManager(context, 4)
        recyclerView.adapter = adapter
    }

    override fun openAlbum(albumId: Int) {
        val directions =
            AlbumListFragmentDirections.actionAlbumListFragmentToPictureListFragment(
                albumId
            )
        NavHostFragment.findNavController(this).navigate(directions)
    }

    private fun initializeView() {
        val toolbar = parentActivity().toolbar
        toolbar!!.title = getString(R.string.albums)
        toolbar.navigationIcon = null

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(AlbumListViewModel::class.java)

        getAlbums()

    }

    private fun getAlbums() {
        lifecycleScope.launch {
            viewModel.getAlbums()?.observe(viewLifecycleOwner, Observer {
                albums_loading_progressbar.visibility = View.INVISIBLE
                adapter.submitList(it)
            })
        }

    }

}

interface OnAlbumEventListener {
    fun openAlbum(albumId: Int)
}
