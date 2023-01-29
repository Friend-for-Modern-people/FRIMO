package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.R
import gachon.teama.frimo.data.local.AppDatabase
import gachon.teama.frimo.data.remote.Server
import gachon.teama.frimo.databinding.FragmentDiaryFilteredSentimentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryFilteredBySentimentFragment : Fragment() {

    // Binding
    private val binding by lazy { FragmentDiaryFilteredSentimentBinding.inflate(layoutInflater) }

    // User
    private val userId by lazy { AppDatabase.getInstance(requireContext())!!.userDao().getUserId() }

    /**
     * @description - 생명주기 onCreateView
     * @param - inflater(LayoutInflater)
     * @param - container(ViewGroup)
     * @param - savedInstanceState(Bundle)
     * @return - v(View)
     * @author - namsh1125
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setScreen()
        return binding.root
    }

    /**
     * @description - Set screen
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setScreen() {
        setAnger()
        setSadness()
        setAnxiety()
        setWound()
        setEmbarrassment()
        setPleasure()
    }

    /**
     * @description - 분노로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setAnger() {

        CoroutineScope(Dispatchers.Main).launch {
            val diaries = Server.getDiaryBySentiment(userId, Sentiment.Anger.value)

            // Set layout
            binding.textviewDiaryAngerCount.text = getString(R.string.set_diary_count, diaries.size)

            // Set layout (anger detail) click listener
            binding.layoutAngerDetail.setOnClickListener {
                val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                intent.apply {
                    this.putExtra("filter", "#분노") // 어떤 필터가 걸려있는지 전달
                    this.putExtra("filteredDiary", diaries) // 필터링된 diary 전달
                }
                startActivity(intent)
            }

            // Set anger diary 1
            if (diaries.size >= 1) {
                binding.angerDiary1.visibility = View.VISIBLE
                binding.angerDiary2.visibility = View.INVISIBLE

                binding.textviewAngerDiary1Date.text = diaries[0].createdString
            }

            // Set anger diary 1 click listener
            binding.angerDiary1.setOnClickListener {
                val intent = Intent(requireContext(), DiaryActivity::class.java)
                intent.putExtra("id", diaries[0].id) // Diary id 전달
                startActivity(intent)
            }

            // Set anger diary 2
            if (diaries.size >= 2) {
                binding.angerDiary2.visibility = View.VISIBLE
                binding.textviewAngerDiary2Date.text = diaries[1].createdString
            }

            // Set anger diary 2 click listener
            binding.angerDiary2.setOnClickListener {
                val intent = Intent(requireContext(), DiaryActivity::class.java)
                intent.putExtra("id", diaries[1].id) // Diary id 전달
                startActivity(intent)
            }
        }
    }

    /**
     * @description - 슬픔으로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setSadness() {

        CoroutineScope(Dispatchers.Main).launch {
            val diaries = Server.getDiaryBySentiment(userId, Sentiment.Sadness.value)

            // Set layout
            binding.textviewDiarySadnessCount.text = getString(R.string.set_diary_count, diaries.size)

            // Set layout (sadness detail) click listener
            binding.layoutSadnessDetail.setOnClickListener {
                val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                intent.apply {
                    this.putExtra("filter", "#슬픔") // 어떤 필터가 걸려있는지 전달
                    this.putExtra("filteredDiary", diaries) // 필터링된 diary 전달
                }
                startActivity(intent)
            }

            // Set sadness diary 1
            if (diaries.size >= 1) {
                binding.sadnessDiary1.visibility = View.VISIBLE
                binding.sadnessDiary2.visibility = View.INVISIBLE

                binding.textviewSadnessDiary1Date.text = diaries[0].createdString
            }

            // Set sadness diary 1 click listener
            binding.sadnessDiary1.setOnClickListener {
                val intent = Intent(requireContext(), DiaryActivity::class.java)
                intent.putExtra("id", diaries[0].id) // Diary id 전달
                startActivity(intent)
            }

            // Set sadness diary 2
            if (diaries.size >= 2) {
                binding.sadnessDiary2.visibility = View.VISIBLE
                binding.textviewSadnessDiary2Date.text = diaries[1].createdString
            }

            // Set sadness diary 2 click listener
            binding.sadnessDiary2.setOnClickListener {
                val intent = Intent(requireContext(), DiaryActivity::class.java)
                intent.putExtra("id", diaries[1].id) // Diary id 전달
                startActivity(intent)
            }
        }
    }

    /**
     * @description - 불안으로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setAnxiety() {

        CoroutineScope(Dispatchers.Main).launch {
            val diaries = Server.getDiaryBySentiment(userId, Sentiment.Anxiety.value)

            // Set layout
            binding.textviewDiaryAnxietyCount.text = getString(R.string.set_diary_count, diaries.size)

            // Set layout (anxiety detail) click listener
            binding.layoutAnxietyDetail.setOnClickListener {
                val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                intent.apply {
                    this.putExtra("filter", "#불안") // 어떤 필터가 걸려있는지 전달
                    this.putExtra("filteredDiary", diaries) // 필터링된 diary 전달
                }
                startActivity(intent)
            }

            // Set anxiety diary 1
            if (diaries.size >= 1) {
                binding.anxietyDiary1.visibility = View.VISIBLE
                binding.anxietyDiary2.visibility = View.INVISIBLE

                binding.textviewAnxietyDiary1Date.text = diaries[0].createdString
            }

            // Set anxiety diary 1 click listener
            binding.anxietyDiary1.setOnClickListener {
                val intent = Intent(requireContext(), DiaryActivity::class.java)
                intent.putExtra("id", diaries[0].id) // Diary id 전달
                startActivity(intent)
            }

            // Set anxiety diary 2
            if (diaries.size >= 2) {
                binding.anxietyDiary2.visibility = View.VISIBLE
                binding.textviewAnxietyDiary2Date.text = diaries[1].createdString
            }

            // Set anxiety diary 2 click listener
            binding.anxietyDiary2.setOnClickListener {
                val intent = Intent(requireContext(), DiaryActivity::class.java)
                intent.putExtra("id", diaries[1].id) // Diary id 전달
                startActivity(intent)
            }
        }
    }

    /**
     * @description - 상처로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setWound() {

        CoroutineScope(Dispatchers.Main).launch {
            val diaries = Server.getDiaryBySentiment(userId, Sentiment.Wound.value)

            // Set layout
            binding.textviewDiaryWoundCount.text = getString(R.string.set_diary_count, diaries.size)

            // Set layout (wound detail) click listener
            binding.layoutWoundDetail.setOnClickListener {
                val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                intent.apply {
                    this.putExtra("filter", "#상처") // 어떤 필터가 걸려있는지 전달
                    this.putExtra("filteredDiary", diaries) // 필터링된 diary 전달
                }
                startActivity(intent)
            }

            // Set wound diary 1
            if (diaries.size >= 1) {
                binding.woundDiary1.visibility = View.VISIBLE
                binding.woundDiary2.visibility = View.INVISIBLE

                binding.textviewWoundDiary1Date.text = diaries[0].createdString
            }

            // Set wound diary 1 click listener
            binding.woundDiary1.setOnClickListener {
                val intent = Intent(requireContext(), DiaryActivity::class.java)
                intent.putExtra("id", diaries[0].id) // Diary id 전달
                startActivity(intent)
            }

            // Set wound diary 2
            if (diaries.size >= 2) {
                binding.woundDiary2.visibility = View.VISIBLE
                binding.textviewWoundDiary2Date.text = diaries[1].createdString
            }

            // Set wound diary 2 click listener
            binding.woundDiary2.setOnClickListener {
                val intent = Intent(requireContext(), DiaryActivity::class.java)
                intent.putExtra("id", diaries[1].id) // Diary id 전달
                startActivity(intent)
            }
        }
    }

    /**
     * @description - 당황으로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setEmbarrassment() {

        CoroutineScope(Dispatchers.Main).launch {
            val diaries = Server.getDiaryBySentiment(userId, Sentiment.Embarrassment.value)

            // Set layout
            binding.textviewDiaryEmbarrassmentCount.text = getString(R.string.set_diary_count, diaries.size)

            // Set layout (embarrassment detail) click listener
            binding.layoutEmbarrassmentDetail.setOnClickListener {
                val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                intent.apply {
                    this.putExtra("filter", "#당황") // 어떤 필터가 걸려있는지 전달
                    this.putExtra("filteredDiary", diaries) // 필터링된 diary 전달
                }
                startActivity(intent)
            }

            // Set embarrassment diary 1
            if (diaries.size >= 1) {
                binding.embarrassmentDiary1.visibility = View.VISIBLE
                binding.embarrassmentDiary2.visibility = View.INVISIBLE

                binding.textviewEmbarrassmentDiary1Date.text = diaries[0].createdString
            }

            // Set embarrassment diary 1 click listener
            binding.embarrassmentDiary1.setOnClickListener {
                val intent = Intent(requireContext(), DiaryActivity::class.java)
                intent.putExtra("id", diaries[0].id) // Diary id 전달
                startActivity(intent)
            }

            // Set embarrassment diary 2
            if (diaries.size >= 2) {
                binding.embarrassmentDiary2.visibility = View.VISIBLE
                binding.textviewEmbarrassmentDiary2Date.text = diaries[1].createdString
            }

            // Set embarrassment diary 2 click listener
            binding.embarrassmentDiary2.setOnClickListener {
                val intent = Intent(requireContext(), DiaryActivity::class.java)
                intent.putExtra("id", diaries[1].id) // Diary id 전달
                startActivity(intent)
            }
        }
    }

    /**
     * @description - 기쁨으로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setPleasure() {

        CoroutineScope(Dispatchers.Main).launch {
            val diaries = Server.getDiaryBySentiment(userId, Sentiment.Pleasure.value)

            // Set layout
            binding.textviewDiaryPleasureCount.text = getString(R.string.set_diary_count, diaries.size)

            // Set layout (pleasure detail) click listener
            binding.layoutPleasureDetail.setOnClickListener {
                val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
                intent.apply {
                    this.putExtra("filter", "#기쁨") // 어떤 필터가 걸려있는지 전달
                    this.putExtra("filteredDiary", diaries) // 필터링된 diary 전달
                }
                startActivity(intent)
            }

            // Set pleasure diary 1
            if (diaries.size >= 1) {
                binding.pleasureDiary1.visibility = View.VISIBLE
                binding.pleasureDiary2.visibility = View.INVISIBLE

                binding.textviewPleasureDiary1Date.text = diaries[0].createdString
            }

            // Set pleasure diary 1 click listener
            binding.pleasureDiary1.setOnClickListener {
                val intent = Intent(requireContext(), DiaryActivity::class.java)
                intent.putExtra("id", diaries[0].id) // Diary id 전달
                startActivity(intent)
            }

            // Set pleasure diary 2
            if (diaries.size >= 2) {
                binding.pleasureDiary2.visibility = View.VISIBLE
                binding.textviewPleasureDiary2Date.text = diaries[1].createdString
            }

            // Set pleasure diary 2 click listener
            binding.pleasureDiary2.setOnClickListener {
                val intent = Intent(requireContext(), DiaryActivity::class.java)
                intent.putExtra("id", diaries[1].id) // Diary id 전달
                startActivity(intent)
            }
        }
    }

    enum class Sentiment(val value: Int) {
        Anger(0), Sadness(1), Anxiety(2), Wound(3), Embarrassment(4), Pleasure(5)
    }
}