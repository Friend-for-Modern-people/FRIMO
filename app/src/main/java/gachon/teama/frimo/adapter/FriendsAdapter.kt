package gachon.teama.frimo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gachon.teama.frimo.R
import gachon.teama.frimo.data.entities.Friend
import gachon.teama.frimo.ui.SetCharacterActivity

class FriendsAdapter(private val dataSet: ArrayList<Friend>) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView_name: TextView
        val textview_tag: TextView
        val imageView_friend: ImageView

        init {
            textView_name = view.findViewById(R.id.textview_friend_name)
            textview_tag = view.findViewById(R.id.textview_friend_tag)
            imageView_friend = view.findViewById(R.id.imageview_friend)
            imageView_friend.clipToOutline = true // 이미지를 배경에 맞게 자르기
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.view_friends, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView_name.text = dataSet[position].name
        // Todo: tag 설정

        viewHolder.imageView_friend.setImageResource(dataSet[position].img_theme)

        // View click listener
        viewHolder.imageView_friend.setOnClickListener {
            val intent = Intent(it.context, SetCharacterActivity::class.java)
            intent.putExtra("id", dataSet[position].id)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataSet.size
}