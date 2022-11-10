package gachon.teama.frimo.data.remote

import java.util.*

data class Chat(
    val who : String = "null",
    val message : String = "null",
    val time : Date = Date()
)