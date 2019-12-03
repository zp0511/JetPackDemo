package com.demo.jetpack.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.jetpack.R
import com.demo.jetpack.network.RetrofitManager
import kotlinx.android.synthetic.main.activity_association.*
import kotlinx.coroutines.*

/**
 * Created by zp on 2019/10/16.
 */
class AssociationActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_association)
        tvAction.setOnClickListener {
            CoroutineScope(coroutineContext).launch(Dispatchers.Main){
               val data =  withContext(Dispatchers.IO){
                    val data = RetrofitManager.apiService.homeArticle(1).data
                    data
                }
                tvContent.text = data.toString()
            }
        }
    }
}