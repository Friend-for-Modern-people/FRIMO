package gachon.teama.frimo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import gachon.teama.frimo.R
import gachon.teama.frimo.data.entities.Words

class WordsAdapter(private val dataSet: ArrayList<Words>) : RecyclerView.Adapter<WordsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textview: TextView

        init {
            textview = view.findViewById(R.id.textview_word)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.view_word_i_wrote, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textview.text = dataSet[position].word

        // Set textview background and color
        when(dataSet[position].sentiment){
            anger -> {
                viewHolder.textview.setBackgroundResource(R.drawable.shape_words_anger_related)
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.anger))
            }
            sadness -> {
                viewHolder.textview.setBackgroundResource(R.drawable.shape_words_sadness_related)
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.sadness))
            }
            anxiety -> {
                viewHolder.textview.setBackgroundResource(R.drawable.shape_words_anxiety_related)
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.anxiety))
            }
            wound -> {
                viewHolder.textview.setBackgroundResource(R.drawable.shape_words_wound_related)
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.wound))
            }
            embarrassment -> {
                viewHolder.textview.setBackgroundResource(R.drawable.shape_words_embarrassment_related)
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.embarrassment))
            }
            pleasure -> {
                viewHolder.textview.setBackgroundResource(R.drawable.shape_words_pleasure_related)
                viewHolder.textview.setTextColor(ContextCompat.getColor(viewHolder.itemView.context, R.color.pleasure))
            }
        }
    }

    override fun getItemCount() = dataSet.size

    companion object Sentiment{
        const val anger = 0
        const val sadness = 1
        const val anxiety = 2
        const val wound = 3
        const val embarrassment = 4
        const val pleasure = 5
    }
}