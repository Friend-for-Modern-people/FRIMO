package gachon.teama.frimo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import gachon.teama.frimo.data.remote.Diary
import gachon.teama.frimo.databinding.FragmentDiaryFilteredSentimentBinding
import kotlin.collections.ArrayList

class DiaryFilteredBySentimentFragment : Fragment() {

    // Binding
    private val binding by lazy { FragmentDiaryFilteredSentimentBinding.inflate(layoutInflater) }

    /**
     * @description - 생명주기 onCreateView
     * @param - inflater(LayoutInflater)
     * @param - container(ViewGroup)
     * @param - savedInstanceState(Bundle)
     * @return - v(View)
     * @author - namsh1125
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

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

        val diary = getDiary(anger)

        // Set layout
        binding.textviewDiaryAngerCount.text = "${diary.size}개"

        // Set layout (anger detail) click listener
        binding.layoutAngerDetail.setOnClickListener {

            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
            intent.putExtra("filter", "#분노") // 어떤 필터가 걸려있는지 전달
            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
            startActivity(intent)
        }

        // Set visibility
        if (diary.size == 0) {
            binding.layoutAnger.visibility = View.GONE
        }

        // Set anger diary 1
        if (diary.size >= 1) {
            binding.textviewAngerDiary1Date.text = diary[0].created
        } else {
            binding.angerDiary1.visibility = View.INVISIBLE
        }

        // Set anger diary 1 click listener
        binding.angerDiary1.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[0].id) // Diary id 전달
            startActivity(intent)
        }

        // Set anger diary 2
        if (diary.size >= 2) {
            binding.textviewAngerDiary2Date.text = diary[1].created
        } else {
            binding.angerDiary2.visibility = View.INVISIBLE
        }

        // Set anger diary 2 click listener
        binding.angerDiary2.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[1].id) // Diary id 전달
            startActivity(intent)
        }

    }

    /**
     * @description - 슬픔으로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setSadness() {

        val diary = getDiary(sadness)

        // Set layout
        binding.textviewDiarySadnessCount.text = "${diary.size}개"

        // Set layout (sadness detail) click listener
        binding.layoutSadnessDetail.setOnClickListener {

            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
            intent.putExtra("filter", "#슬픔") // 어떤 필터가 걸려있는지 전달
            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
            startActivity(intent)
        }

        // Set visibility
        if (diary.size == 0) {
            binding.layoutSadness.visibility = View.GONE
        }

        // Set sadness diary 1
        if (diary.size >= 1) {
            binding.textviewSadnessDiary1Date.text = diary[0].created
        } else {
            binding.sadnessDiary1.visibility = View.INVISIBLE
        }

        // Set sadness diary 1 click listener
        binding.sadnessDiary1.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[0].id) // Diary id 전달
            startActivity(intent)
        }

        // Set sadness diary 2
        if (diary.size >= 2) {
            binding.textviewSadnessDiary2Date.text = diary[1].created
        } else {
            binding.sadnessDiary2.visibility = View.INVISIBLE
        }

        // Set sadness diary 2 click listener
        binding.sadnessDiary2.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[1].id) // Diary id 전달
            startActivity(intent)
        }

    }

    /**
     * @description - 불안으로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setAnxiety() {

        val diary = getDiary(anxiety)

        // Set layout
        binding.textviewDiaryAnxietyCount.text = "${diary.size}개"

        // Set layout (anxiety detail) click listener
        binding.layoutAnxietyDetail.setOnClickListener {

            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
            intent.putExtra("filter", "#불안") // 어떤 필터가 걸려있는지 전달
            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
            startActivity(intent)
        }

        // Set visibility
        if (diary.size == 0) {
            binding.layoutAnxiety.visibility = View.GONE
        }

        // Set anxiety diary 1
        if (diary.size >= 1) {
            binding.textviewAnxietyDiary1Date.text = diary[0].created
        } else {
            binding.anxietyDiary1.visibility = View.INVISIBLE
        }

        // Set anxiety diary 1 click listener
        binding.anxietyDiary1.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[0].id) // Diary id 전달
            startActivity(intent)
        }

        // Set anxiety diary 2
        if (diary.size >= 2) {
            binding.textviewAnxietyDiary2Date.text = diary[1].created
        } else {
            binding.anxietyDiary2.visibility = View.INVISIBLE
        }

        // Set anxiety diary 2 click listener
        binding.anxietyDiary2.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[1].id) // Diary id 전달
            startActivity(intent)
        }

    }

    /**
     * @description - 상처로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setWound() {

        val diary = getDiary(wound)

        // Set layout
        binding.textviewDiaryWoundCount.text = "${diary.size}개"

        // Set layout (wound detail) click listener
        binding.layoutWoundDetail.setOnClickListener {

            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
            intent.putExtra("filter", "#상처") // 어떤 필터가 걸려있는지 전달
            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
            startActivity(intent)
        }

        // Set visibility
        if (diary.size == 0) {
            binding.layoutWound.visibility = View.GONE
        }

        // Set wound diary 1
        if (diary.size >= 1) {
            binding.textviewWoundDiary1Date.text = diary[0].created
        } else {
            binding.woundDiary1.visibility = View.INVISIBLE
        }

        // Set wound diary 1 click listener
        binding.woundDiary1.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[0].id) // Diary id 전달
            startActivity(intent)
        }

        // Set wound diary 2
        if (diary.size >= 2) {
            binding.textviewWoundDiary2Date.text = diary[1].created
        } else {
            binding.woundDiary2.visibility = View.INVISIBLE
        }

        // Set wound diary 2 click listener
        binding.woundDiary2.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[1].id) // Diary id 전달
            startActivity(intent)
        }

    }

    /**
     * @description - 당황으로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setEmbarrassment() {

        val diary = getDiary(embarrassment)

        // Set layout
        binding.textviewDiaryEmbarrassmentCount.text = "${diary.size}개"

        // Set layout (embarrassment detail) click listener
        binding.layoutEmbarrassmentDetail.setOnClickListener {

            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
            intent.putExtra("filter", "#당황") // 어떤 필터가 걸려있는지 전달
            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
            startActivity(intent)
        }

        // Set visibility
        if (diary.size == 0) {
            binding.layoutEmbarrassment.visibility = View.GONE
        }

        // Set embarrassment diary 1
        if (diary.size >= 1) {
            binding.textviewEmbarrassmentDiary1Date.text = diary[0].created
        } else {
            binding.embarrassmentDiary1.visibility = View.INVISIBLE
        }

        // Set embarrassment diary 1 click listener
        binding.embarrassmentDiary1.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[0].id) // Diary id 전달
            startActivity(intent)
        }

        // Set embarrassment diary 2
        if (diary.size >= 2) {
            binding.textviewEmbarrassmentDiary2Date.text = diary[1].created
        } else {
            binding.embarrassmentDiary2.visibility = View.INVISIBLE
        }

        // Set embarrassment diary 2 click listener
        binding.embarrassmentDiary2.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[1].id) // Diary id 전달
            startActivity(intent)
        }

    }

    /**
     * @description - 기쁨으로 filtering된 diary를 화면에 설정
     * @param - None
     * @return - None
     * @author - namsh1125
     */
    private fun setPleasure() {

        val diary = getDiary(pleasure)

        // Set layout
        binding.textviewDiaryPleasureCount.text = "${diary.size}개"

        // Set layout (pleasure detail) click listener
        binding.layoutPleasureDetail.setOnClickListener {

            val intent = Intent(requireContext(), FilteredDetailDiaryActivity::class.java)
            intent.putExtra("filter", "#기쁨") // 어떤 필터가 걸려있는지 전달
            intent.putExtra("filteredDiary", diary) // 필터링된 diary 전달
            startActivity(intent)
        }

        // Set visibility
        if (diary.size == 0) {
            binding.layoutPleasure.visibility = View.GONE
        }

        // Set pleasure diary 1
        if (diary.size >= 1) {
            binding.textviewPleasureDiary1Date.text = diary[0].created
        } else {
            binding.pleasureDiary1.visibility = View.INVISIBLE
        }

        // Set pleasure diary 1 click listener
        binding.pleasureDiary1.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[0].id) // Diary id 전달
            startActivity(intent)
        }

        // Set pleasure diary 2
        if (diary.size >= 2) {
            binding.textviewPleasureDiary2Date.text = diary[1].created
        } else {
            binding.pleasureDiary2.visibility = View.INVISIBLE
        }

        // Set pleasure diary 2 click listener
        binding.pleasureDiary2.setOnClickListener {

            val intent = Intent(requireContext(), DiaryActivity::class.java)
            intent.putExtra("id", diary[1].id) // Diary id 전달
            startActivity(intent)
        }

    }

    /**
     * @description - Server에서 filtering된 diary 가져오기
     * @param - sentiment(Int) : 필터링 기준
     * @return - diaries(Arraylist<Diary>) : 필터링된 diary들 (감정)
     * @author - namsh1125
     */
    private fun getDiary(sentiment: Int): ArrayList<Diary> {

        val diary: MutableList<Diary> = mutableListOf()

        // Todo: 서버에서 감정별로 filtering된 diary 가져오기


        return diary as ArrayList<Diary>
    }

    companion object Sentiment {
        const val anger = 0
        const val sadness = 1
        const val anxiety = 2
        const val wound = 3
        const val embarrassment = 4
        const val pleasure = 5
    }

}