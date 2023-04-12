package gachon.teama.frimo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gachon.teama.frimo.R
import gachon.teama.frimo.data.remote.Chat
import java.text.SimpleDateFormat

class ChatAdapter(private val chatList: MutableList<Chat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = when (viewType) {
            Position.Left.value -> inflater.inflate(R.layout.view_chatting_left, parent, false)
            Position.Right.value -> inflater.inflate(R.layout.view_chatting_right, parent, false)
            else -> inflater.inflate(R.layout.view_chatting_center, parent, false)
        }
        return when (viewType) {
            Position.Left.value -> LeftViewHolder(view)
            Position.Right.value -> RightViewHolder(view)
            else -> CenterViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = chatList[position]
        val formatter = SimpleDateFormat("h:mm a")

        when (holder) {
            is CenterViewHolder -> holder.textView.text = chat.message
            is LeftViewHolder -> {
                holder.textviewNickname.text = chat.who
                holder.textviewMessage.text = chat.message
                holder.textviewTime.text = formatter.format(chat.time)
            }
            is RightViewHolder -> {
                holder.textviewMessage.text = chat.message
                holder.textviewTime.text = formatter.format(chat.time)
            }
        }
    }

    override fun getItemCount() = chatList.size

    override fun getItemViewType(position: Int): Int {
        return when (chatList[position].who) {
            "FRIMO" -> Position.Left.value
            "Me" -> Position.Right.value
            else -> Position.Center.value
        }
    }

    fun addChat(chat: Chat) {
        chatList.add(chat)
        notifyItemInserted(chatList.size - 1)
    }

    enum class Position(val value: Int) {
        Left(1), Center(2), Right(3)
    }

    class CenterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textview)
    }

    class LeftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageview_profile)
        val textviewNickname: TextView = view.findViewById(R.id.textview_nickname)
        val textviewMessage: TextView = view.findViewById(R.id.textview_message)
        val textviewTime: TextView = view.findViewById(R.id.textview_time)
    }

    class RightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textviewMessage: TextView = view.findViewById(R.id.textview_message)
        val textviewTime: TextView = view.findViewById(R.id.textview_time)
    }
}