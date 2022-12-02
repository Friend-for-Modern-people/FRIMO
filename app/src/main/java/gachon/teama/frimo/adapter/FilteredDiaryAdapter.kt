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
import gachon.teama.frimo.ui.FilteredDiaryFragment

/**
 * @see gachon.teama.frimo.ui.FilteredDetailDiaryActivity
 *
 * FilteredDetailDiaryActivity에서 diary 보여주는데 사용
 */
class FilteredDiaryAdapter(private val dataSet: ArrayList<Diary>) :
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

        viewHolder.textView_date.text = dataSet[position].created
        viewHolder.textView_sentiment.text = getTextSentiment(dataSet[position].sentiment)

        // 이미지 배경 셋팅
        // Fixme: 함수로 간단하게 할 수 없을까?
        when (dataSet[position].sentiment) {
            pleasure -> viewHolder.imageView.background.setTint(
                viewHolder.itemView.context.resources.getColor(
                    R.color.pleasure
                )
            )

            sadness -> {
                viewHolder.imageView.background.setTint(
                    viewHolder.itemView.context.resources.getColor(
                        R.color.sadness
                    )
                )
            }
            anxiety -> {
                viewHolder.imageView.background.setTint(
                    viewHolder.itemView.context.resources.getColor(
                        R.color.anxiety
                    )
                )
            }
            wound -> {
                viewHolder.imageView.background.setTint(
                    viewHolder.itemView.context.resources.getColor(
                        R.color.wound
                    )
                )
            }
            embarrassment -> {
                viewHolder.imageView.background.setTint(
                    viewHolder.itemView.context.resources.getColor(
                        R.color.embarrassment
                    )
                )
            }
            anger -> {
                viewHolder.imageView.background.setTint(
                    viewHolder.itemView.context.resources.getColor(
                        R.color.anger
                    )
                )
            }
        }

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
        return when(sentiment){
            anger -> "# 분노"
            sadness -> "# 슬픔"
            anxiety -> "# 불안"
            wound -> "# 상처"
            embarrassment -> "# 당황"
            else -> "# 기쁨"
        }
    }

    companion object Sentiment {
        const val anger = 1
        const val sadness = 2
        const val anxiety = 3
        const val wound = 4
        const val embarrassment = 5
        const val pleasure = 6
    }

}
