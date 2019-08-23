package com.example.bdiw.main.mainfragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bdiw.R
import com.example.bdiw.main.utils.Utils
import kotlinx.android.synthetic.main.fragment1_view.*

class Fragment1 : Fragment(){

      var fragment1Context:Context?=null
      var isVisibleForUser:Boolean? = null
      val TAG = "tag"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.fragment1_view,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userVisibleHint = false

              if(Utils.IS_FIRST_FRAGMENT_RUN_FIRST_TIME){
                  val pd = ProgressDialog(context)
                  Log.e("tag","$isVisible")

                  Utils.setUpProgresDialog(pd)

                  Utils.doRequestForCurentArticles(context!!,articlesRecyclerView)
                  Utils.IS_FIRST_FRAGMENT_RUN_FIRST_TIME = false

              }




    }




    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        Log.e("VISIBIL","$isVisibleToUser")

        isVisibleForUser = isVisibleToUser

        if(!Utils.IS_FIRST_FRAGMENT_RUN_FIRST_TIME){
            if(isVisibleToUser){
                val pd = ProgressDialog(context)
                Log.e("tag","$isVisible")

                Utils.setUpProgresDialog(pd)

                Utils.doRequestForCurentArticles(context!!,articlesRecyclerView)
            }
        }



    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        this.fragment1Context = context
    }


    override fun onDestroy() {
        super.onDestroy()
        Utils.IS_FIRST_FRAGMENT_RUN_FIRST_TIME = true
    }

}