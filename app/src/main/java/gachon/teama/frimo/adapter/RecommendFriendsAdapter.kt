package gachon.teama.frimo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gachon.teama.frimo.R
import gachon.teama.frimo.data.entities.Friend

class RecommendFriendsAdapter(private val dataSet: ArrayList<Friend>) : RecyclerView.Adapter<RecommendFriendsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView
        val imageView: ImageView

        init {
            textView = view.findViewById(R.id.textview_recommend_friend)
            imageView = view.findViewById(R.id.imageView_recommend_friend)
            imageView.clipToOutline = true // 이미지를 배경에 맞게 자르기

            // Define click listener for the ViewHolder's View.

        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.view_friends_recommend, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = dataSet[position].name
        viewHolder.imageView.setImageResource(dataSet[position].img_recommendation)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}