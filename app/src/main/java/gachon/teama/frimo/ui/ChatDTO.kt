package gachon.teama.frimo.ui

import java.util.*

data class ChatDTO(
    val who : String = "null",
    val message : String = "null",
    val time : Date = Date()
)