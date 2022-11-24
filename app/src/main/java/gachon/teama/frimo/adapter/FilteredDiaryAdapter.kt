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
        viewHolder.textView_sentiment.text = dataSet[position].sentiment

        // Todo: 이미지 셋팅
        when(dataSet[position].sentiment){
            "# 기쁨" -> {
                viewHolder.imageView.background.setTint(viewHolder.itemView.context.resources.getColor(R.color.pleasure))
            }
            "# 슬픔" -> {
                viewHolder.imageView.background.setTint(viewHolder.itemView.context.resources.getColor(R.color.sadness))
            }
            "# 불안" -> {
                viewHolder.imageView.background.setTint(viewHolder.itemView.context.resources.getColor(R.color.anxiety))
            }
            "# 상처" -> {
                viewHolder.imageView.background.setTint(viewHolder.itemView.context.resources.getColor(R.color.wound))
            }
            "# 당황" -> {
                viewHolder.imageView.background.setTint(viewHolder.itemView.context.resources.getColor(R.color.embarrassment))
            }
            else -> {
                viewHolder.imageView.background.setTint(viewHolder.itemView.context.resources.getColor(R.color.anger))
            }
        }

        // When recyclerview item(diary) clicked
        // Fixme: viewHolder class 안에 선언하는 방법이 무엇이 있을까?
        viewHolder.itemView.setOnClickListener {
            val intent = Intent(it.context, DiaryActivity::class.java)
            intent.putExtra("id", dataSet[position].id)
            it.context.startActivity(intent)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
