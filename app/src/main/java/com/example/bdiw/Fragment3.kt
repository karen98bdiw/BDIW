package com.example.bdiw

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

class Fragment3 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment3_view,container,false)
        return view
    }

}