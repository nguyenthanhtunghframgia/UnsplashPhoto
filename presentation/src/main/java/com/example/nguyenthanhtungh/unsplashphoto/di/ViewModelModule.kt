package com.example.nguyenthanhtungh.unsplashphoto.di

import com.example.nguyenthanhtungh.unsplashphoto.ui.collectiondetail.CollectionDetailViewModel
import com.example.nguyenthanhtungh.unsplashphoto.ui.home.FragmentHomeViewModel
import com.example.nguyenthanhtungh.unsplashphoto.ui.main.MainViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module(override = true) {
    viewModel { MainViewModel() }
    viewModel { FragmentHomeViewModel(get(), get()) }
    viewModel { CollectionDetailViewModel(get(), get()) }
}
