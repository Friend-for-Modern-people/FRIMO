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
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.view_diary, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.textView_date.text = dataSet[position].createdString
        viewHolder.textView_sentiment.text = getTextSentiment(dataSet[position].sentiment)
        viewHolder.imageView.background.setTint(viewHolder.itemView.context.resources.getColor(getSentimentColor(dataSet[position].sentiment)))

        // When recyclerview item(diary) clicked
        viewHolder.itemView.setOnClickListener {
            val intent = Intent(it.context, DiaryActivity::class.java)
            intent.putExtra("id", dataSet[position].id)
            it.context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    private fun getTextSentiment(sentiment: Int): String {
        return when (sentiment) {
            Sentiment.Anger.value -> "#분노"
            Sentiment.Sadness.value -> "#슬픔"
            Sentiment.Anxiety.value -> "#불안"
            Sentiment.Wound.value -> "#상처"
            Sentiment.Embarrassment.value -> "#당황"
            Sentiment.Pleasure.value -> "#기쁨"
            else -> "#에러"
        }
    }

    /**
     * @description - diary의 감정에 맞는 배경화면 색상을 return
     * @param - sentiment(Int) : 해당 diary의 대표 감정
     * @return - color(Int) : 해당 diary의 배경화면 색상
     * @author - namsh1125
     */
    fun getSentimentColor(sentiment: Int): Int {
        return when (sentiment) {
            Sentiment.Pleasure.value -> R.color.pleasure
            Sentiment.Sadness.value -> R.color.sadness
            Sentiment.Anxiety.value -> R.color.anxiety
            Sentiment.Wound.value -> R.color.wound
            Sentiment.Embarrassment.value -> R.color.embarrassment
            Sentiment.Anger.value -> R.color.anger
            else -> R.color.black
        }
    }

    enum class Sentiment(val value: Int) {
        Anger(0), Sadness(1), Anxiety(2), Wound(3), Embarrassment(4), Pleasure(5)
    }

}
