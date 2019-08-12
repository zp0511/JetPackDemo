package com.demo.jetpack.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.demo.jetpack.R
import kotlinx.android.synthetic.main.fragment_welcome.*

/**
 * Created by zp on 2019/8/8.
 */
class WelComeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("key", "value")
            findNavController().navigate(R.id.login, bundle)
        }
        btn_register.setOnClickListener {
            val direction =
                WelComeFragmentDirections
                    .actionWelcomeRegister()
                    .setParameter("kchome")
            findNavController().navigate(direction)
        }
    }
}