package gachon.teama.frimo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gachon.teama.frimo.data.local.Friend
import gachon.teama.frimo.databinding.ViewFriendsRecommendBinding
import gachon.teama.frimo.ui.SetCharacterActivity

class RecommendFriendsAdapter(private val dataSet: ArrayList<Friend>) : RecyclerView.Adapter<RecommendFriendsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ViewFriendsRecommendBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageViewRecommendFriend.clipToOutline = true

            // View click listener
            binding.root.setOnClickListener {
                val intent = Intent(it.context, SetCharacterActivity::class.java)
                intent.putExtra("id", adapterPosition + 1)
                it.context.startActivity(intent)
            }
        }

        fun bind(friend: Friend) {
            binding.textviewRecommendFriend.text = friend.name
            binding.imageViewRecommendFriend.setImageResource(friend.img_recommendation)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewFriendsRecommendBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size
}