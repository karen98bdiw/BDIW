package com.example.bdiw.communication.models

 class Chat(
    val sender:String = "",
    val receiver:String = "",
    val messages: HashMap<String, Message> = HashMap<String, Message>()

)
