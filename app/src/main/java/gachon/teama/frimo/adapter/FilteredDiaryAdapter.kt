package gachon.teama.frimo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gachon.teama.frimo.R
import gachon.teama.frimo.data.remote.Diary
import gachon.teama.frimo.ui.DiaryActivity

class FilteredDiaryAdapter(private val dataSet: List<Diary>) : RecyclerView.Adapter<FilteredDiaryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var textviewDate: TextView
        private var textviewSentiment: TextView
        private var imageView: ImageView

        init {
            textviewDate = view.findViewById(R.id.textview_diary_date)
            textviewSentiment = view.findViewById(R.id.textview_diary_sentiment)
            imageView = view.findViewById(R.id.imageView_diary)
        }

        fun bind(diary: Diary) {
            textviewDate.text = diary.createdString
            textviewSentiment.text = diary.getTextSentiment()
            imageView.background.setTint(itemView.context.resources.getColor(diary.getSentimentColor()))

            itemView.setOnClickListener {
                val intent = Intent(it.context, DiaryActivity::class.java)
                intent.putExtra("id", diary.id)
                it.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.view_diary, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val diary = dataSet[position]
        viewHolder.bind(diary)
    }

    override fun getItemCount() = dataSet.size

    private fun getDiary(position: Int): Diary {
        return dataSet[position]
    }
}
