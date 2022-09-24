package gachon.teama.frimo

import java.util.*

data class ChatDTO(
    val nickname : String = "null",
    val message : String = "null",
    val time : Date = Date()
)