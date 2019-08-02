package com.example.bdiw.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bdiw.Models.Message
import com.example.bdiw.R
import com.example.bdiw.Utils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.message_recycler_item_left.view.*
import kotlinx.android.synthetic.main.message_recycler_item_right.view.*

class MessageRecyclerViewAdapter(messages:List<Message>) : RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder>() {

    val data:List<Message>
    val mAuth:FirebaseAuth = FirebaseAuth.getInstance()

    init {
        this.data = messages
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view:View

        if(viewType.equals(Utils.MESAGE_VIEW_LEFT)){
             view = inflater.inflate(R.layout.message_recycler_item_left,parent,false)
            Log.e("inflate","inflatedLeft")
        }else{
            Log.e("inflate","inflatedRight:${viewType}")
            view = inflater.inflate(R.layout.message_recycler_item_right,parent,false)
        }

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val curentMessage = data.get(position)


         holder.messageView.setText(curentMessage.message)


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
          val messageView = itemView.findViewById<TextView>(R.id.showMessageView)
    }

    override fun getItemViewType(position: Int): Int {
        if(data.get(position).sender.equals(mAuth.currentUser!!.uid)){
            Log.e("return","returned right")
            return Utils.MESAGE_VIEW_RIGHT
        }else{
            Log.e("return","returned left")
            return Utils.MESAGE_VIEW_LEFT
        }
    }
}