package gachon.teama.frimo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class ChatAdapter(var myDataList: MutableList<DataItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

        return if (viewType == ChatWindowLocation.Center.content) {
            view = inflater.inflate(R.layout.chatting_center, viewGroup, false)
            CenterViewHolder(view)
        } else if (viewType == ChatWindowLocation.Left.content) {
            view = inflater.inflate(R.layout.chatting_left, viewGroup, false)
            LeftViewHolder(view)
        } else {
            view = inflater.inflate(R.layout.chatting_right, viewGroup, false)
            RightViewHolder(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        val formatter = SimpleDateFormat("h:mm a")

        if (viewHolder is CenterViewHolder) {
            viewHolder.textView.setText(myDataList.get(position).content)
        } else if (viewHolder is LeftViewHolder) {
            viewHolder.textView_nickname.setText(myDataList.get(position).name)
            viewHolder.textView_message.setText(myDataList.get(position).content)
            viewHolder.textView_time.setText(formatter.format(myDataList.get(position).time))
        } else if (viewHolder is RightViewHolder) {
            viewHolder.textView_message.setText(myDataList.get(position).content)
            viewHolder.textView_time.setText(formatter.format(myDataList.get(position).time))
        }

    }

    override fun getItemCount() = myDataList.size

    override fun getItemViewType(position: Int): Int {
        return myDataList[position].viewType
    }

    fun addChat(chat: DataItem) {
        myDataList.add(chat)
        notifyItemInserted(myDataList.size - 1) // 갱신
    }

}
