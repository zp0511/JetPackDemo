package com.demo.jetpack.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.jetpack.R
import com.demo.jetpack.databinding.FragmentLikeBinding

/**
 * Created by zp on 2019/8/9.
 */
class LikeFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LikeModel() as T
            }
        }).get(LikeModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentLikeBinding>(inflater, R.layout.fragment_like, container, false)
        binding.data = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

}