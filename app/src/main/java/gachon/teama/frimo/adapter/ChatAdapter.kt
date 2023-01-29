package gachon.teama.frimo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gachon.teama.frimo.R
import gachon.teama.frimo.data.remote.Chat
import java.text.SimpleDateFormat

class ChatAdapter(private var ChatList: MutableList<Chat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class CenterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            textView = view.findViewById(R.id.textview)
        }
    }

    class LeftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView
        val textView_nickname: TextView
        val textView_message: TextView
        val textView_time: TextView

        init {
            imageView = view.findViewById(R.id.imageview_profile)
            textView_nickname = view.findViewById(R.id.textview_nickname)
            textView_message = view.findViewById(R.id.textview_message)
            textView_time = view.findViewById(R.id.textview_time)
        }
    }

    class RightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView_message: TextView
        val textView_time: TextView

        init {
            textView_message = view.findViewById(R.id.textview_message)
            textView_time = view.findViewById(R.id.textview_time)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        val context: Context = viewGroup.context
        val inflater: LayoutInflater = LayoutInflater.from(context)

        return when (viewType) {
            Position.Left.value -> {
                view = inflater.inflate(R.layout.view_chatting_left, viewGroup, false)
                LeftViewHolder(view)
            }
            Position.Right.value -> {
                view = inflater.inflate(R.layout.view_chatting_right, viewGroup, false)
                RightViewHolder(view)
            }
            else -> {
                view = inflater.inflate(R.layout.view_chatting_center, viewGroup, false)
                CenterViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val formatter = SimpleDateFormat("h:mm a")

        when (viewHolder) {
            is CenterViewHolder -> {
                viewHolder.textView.text = ChatList[position].message
            }
            is LeftViewHolder -> {
                viewHolder.textView_nickname.text = ChatList[position].who
                viewHolder.textView_message.text = ChatList[position].message
                viewHolder.textView_time.text = formatter.format(ChatList[position].time)
            }
            is RightViewHolder -> {
                viewHolder.textView_message.text = ChatList[position].message
                viewHolder.textView_time.text = formatter.format(ChatList[position].time)
            }
        }

    }

    override fun getItemCount() = ChatList.size

    override fun getItemViewType(position: Int): Int {
        return when (ChatList[position].who) {
            "FRIMO" -> Position.Left.value
            "Me" -> Position.Right.value
            else -> Position.Center.value
        }
    }

    fun addChat(chat: Chat) {
        ChatList.add(chat)
        notifyItemInserted(ChatList.size - 1) // 채팅이 추가되었을 때 말풍선 추가 생성
    }

    enum class Position(val value: Int) {
        Left(1), Center(2), Right(3)
    }

}