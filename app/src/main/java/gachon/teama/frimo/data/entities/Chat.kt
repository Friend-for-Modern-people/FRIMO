package gachon.teama.frimo.data.entities

import java.util.*

data class Chat(
    val who : String = "null",
    val message : String = "null",
    val time : Date = Date()
)