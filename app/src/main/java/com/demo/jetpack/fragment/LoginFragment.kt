package com.demo.jetpack.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.demo.jetpack.R
import com.demo.jetpack.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * Created by zp on 2019/8/7.
 */
class LoginFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            activity?.startActivity(intent)
        }
    }
}