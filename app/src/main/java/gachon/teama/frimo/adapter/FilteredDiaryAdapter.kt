package gachon.teama.frimo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gachon.teama.frimo.R
import gachon.teama.frimo.data.entities.Diary
import gachon.teama.frimo.ui.DiaryActivity

class FilteredDiaryAdapter(private val dataSet: List<Diary>) :
    RecyclerView.Adapter<FilteredDiaryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView_date: TextView
        val textView_sentiment: TextView
        val imageView: ImageView

        init {
            // Define click listener for the ViewHolder's View.
            textView_date = view.findViewById(R.id.textview_diary_date)
            textView_sentiment = view.findViewById(R.id.textview_diary_sentiment)
            imageView = view.findViewById(R.id.imageView_diary)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.view_diary, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.textView_date.text = dataSet[position].createdString
        viewHolder.textView_sentiment.text = dataSet[position].getTextSentiment()
        viewHolder.imageView.background.setTint(viewHolder.itemView.context.resources.getColor(dataSet[position].getSentimentColor()))

        // When recyclerview item(diary) clicked
        viewHolder.itemView.setOnClickListener {
            val intent = Intent(it.context, DiaryActivity::class.java)
            intent.putExtra("id", dataSet[position].id)
            it.context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
