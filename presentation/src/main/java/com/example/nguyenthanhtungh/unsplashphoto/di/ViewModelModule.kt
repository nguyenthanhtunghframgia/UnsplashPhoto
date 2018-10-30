package com.example.nguyenthanhtungh.unsplashphoto.di

import com.example.nguyenthanhtungh.unsplashphoto.ui.collectiondetail.CollectionDetailViewModel
import com.example.nguyenthanhtungh.unsplashphoto.ui.collection.CollectionViewModel
import com.example.nguyenthanhtungh.unsplashphoto.ui.discover.DiscoverViewModel
import com.example.nguyenthanhtungh.unsplashphoto.ui.main.MainViewModel
import com.example.nguyenthanhtungh.unsplashphoto.ui.photodetail.PhotoDetailViewModel
import com.example.nguyenthanhtungh.unsplashphoto.ui.search.SearchViewModel
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module

val viewModelModule = module(override = true) {
    viewModel<MainViewModel>()
    viewModel<CollectionViewModel>()
    viewModel<CollectionDetailViewModel>()
    viewModel<PhotoDetailViewModel>()
    viewModel<SearchViewModel>()
    viewModel<DiscoverViewModel>()
}
