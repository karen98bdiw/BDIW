package com.example.bdiw.Models

 class Chat(
    val sender:String = "",
    val receiver:String = "",
    val messages: HashMap<String, Message> = HashMap<String,Message>()

)
