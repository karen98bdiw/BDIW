package com.example.bdiw.main.mainfragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.bdiw.R

class Fragment3 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment3_view,container,false)
        return view
    }

}