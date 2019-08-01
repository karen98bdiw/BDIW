package com.example.bdiw

import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment1_view.*

class Fragment1 : Fragment(){


    

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.fragment1_view,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("requestCalled","${Utils.REQUEST_CALLED}")
         //it will try to get curent news from server bu on failure it will get news archive
         Utils.doRequestForCurentArticles(this@Fragment1.context!!,articlesRecyclerView)
         Utils.REQUEST_CALLED = true



    }





}