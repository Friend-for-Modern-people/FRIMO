package gachon.teama.frimo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gachon.teama.frimo.R
import gachon.teama.frimo.ui.ChatWindowLocation
import gachon.teama.frimo.ui.DataItem
import java.text.SimpleDateFormat

class ChatAdapter(private var myDataList: MutableList<DataItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            ChatWindowLocation.Center.content -> {
                view = inflater.inflate(R.layout.view_chatting_center, viewGroup, false)
                CenterViewHolder(view)
            }
            ChatWindowLocation.Left.content -> {
                view = inflater.inflate(R.layout.view_chatting_left, viewGroup, false)
                LeftViewHolder(view)
            }
            else -> {
                view = inflater.inflate(R.layout.view_chatting_right, viewGroup, false)
                RightViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val formatter = SimpleDateFormat("h:mm a")

        when (viewHolder) {
            is CenterViewHolder -> {
                viewHolder.textView.text = myDataList[position].content
            }
            is LeftViewHolder -> {
                viewHolder.textView_nickname.text = myDataList[position].name
                viewHolder.textView_message.text = myDataList[position].content
                viewHolder.textView_time.text = formatter.format(myDataList[position].time)
            }
            is RightViewHolder -> {
                viewHolder.textView_message.text = myDataList[position].content
                viewHolder.textView_time.text = formatter.format(myDataList[position].time)
            }
        }

    }

    override fun getItemCount() = myDataList.size

    override fun getItemViewType(position: Int): Int {
        return myDataList[position].viewType
    }

    fun addChat(chat: DataItem) {
        myDataList.add(chat)
        notifyItemInserted(myDataList.size - 1) // 채팅이 추가되었을 때 말풍선 추가 생성
    }

}
