package gachon.teama.frimo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gachon.teama.frimo.data.local.Friend
import gachon.teama.frimo.databinding.ViewFriendsBinding
import gachon.teama.frimo.ui.SetCharacterActivity

class FriendsAdapter(private val dataSet: List<Friend>) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    private val datasetSize: Int = dataSet.size

    inner class ViewHolder(private val binding: ViewFriendsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: Friend) {
            binding.textviewFriendName.text = friend.name
            binding.textviewFriendTag.text = friend.tag
            binding.imageviewFriend.setImageResource(friend.img_theme)

            binding.root.setOnClickListener {
                val intent = Intent(it.context, SetCharacterActivity::class.java)
                intent.putExtra("id", friend.id)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = datasetSize
}