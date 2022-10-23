package gachon.teama.frimo

import java.util.*

data class ChatDTO(
    val who : String = "null",
    val message : String = "null",
    val time : Date = Date()
)