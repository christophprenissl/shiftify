package com.christophprenissl.shiftify.view.nurse

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.view.DragEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnDragListener
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.CardShiftBinding
import com.christophprenissl.shiftify.databinding.FragmentPriorityBinding
import com.christophprenissl.shiftify.util.dayMonthYearString
import com.christophprenissl.shiftify.viewmodel.nurse.NurseShiftsViewModel
import java.util.*

class PriorityFragment : Fragment(), View.OnLongClickListener, OnDragListener {

    private lateinit var viewModel: NurseShiftsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPriorityBinding.inflate(inflater)

        viewModel = requireActivity().run {
            ViewModelProviders.of(this)[NurseShiftsViewModel::class.java]
        }

        val observer = Observer<Calendar?> {
            val dateString = viewModel.chosenDay.value?.dayMonthYearString()
            binding.title.text = getString(R.string.priority_title, dateString)
        }
        viewModel.chosenDay.observe(viewLifecycleOwner, observer)

        val earlyShiftCard = CardShiftBinding.inflate(inflater, container, false)
        val lateShiftCard = CardShiftBinding.inflate(inflater, container, false)
        val nightShiftCard = CardShiftBinding.inflate(inflater, container, false)
        val freeShiftCard = CardShiftBinding.inflate(inflater, container, false)
        val holidayShiftCard = CardShiftBinding.inflate(inflater, container, false)
        context?.let {
            earlyShiftCard.root.setBackgroundColor(it.getColor(R.color.shift_early))
            lateShiftCard.root.setBackgroundColor(it.getColor(R.color.shift_late))
            lateShiftCard.shiftTitle.setTextColor(it.getColor(R.color.shift_font_light_color))
            nightShiftCard.root.setBackgroundColor(it.getColor(R.color.shift_night))
            nightShiftCard.shiftTitle.setTextColor(it.getColor(R.color.shift_font_light_color))
            freeShiftCard.root.setBackgroundColor(it.getColor(R.color.shift_free))
            holidayShiftCard.root.setBackgroundColor(it.getColor(R.color.shift_holiday))
        }

        earlyShiftCard.shiftTitle.text = getText(R.string.early_shift_card_label)
        lateShiftCard.shiftTitle.text = getText(R.string.late_shift_card_label)
        nightShiftCard.shiftTitle.text = getText(R.string.night_shift_card_label)
        freeShiftCard.shiftTitle.text = getText(R.string.free_shift_card_label)
        holidayShiftCard.shiftTitle.text = getText(R.string.holiday_shift_card_label)

        binding.prioritySecondPlace.addView(earlyShiftCard.root)
        binding.prioritySecondPlace.addView(lateShiftCard.root)
        binding.prioritySecondPlace.addView(nightShiftCard.root)
        binding.prioritySecondPlace.addView(freeShiftCard.root)
        binding.prioritySecondPlace.addView(holidayShiftCard.root)

        earlyShiftCard.root.setOnLongClickListener(this)
        lateShiftCard.root.setOnLongClickListener(this)
        nightShiftCard.root.setOnLongClickListener(this)
        freeShiftCard.root.setOnLongClickListener(this)
        holidayShiftCard.root.setOnLongClickListener(this)
        binding.priorityFirstPlace.setOnDragListener(this)
        binding.prioritySecondPlace.setOnDragListener(this)
        binding.priorityThirdPlace.setOnDragListener(this)
        binding.priorityFourthPlace.setOnDragListener(this)

        return binding.root
    }

    override fun onLongClick(v: View?): Boolean {

        v?.let {
            val clipText = v.tag.toString()
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            it.visibility = View.INVISIBLE
        }

        return true
    }

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        //handle drag events
        event?.let { it ->
            when (it.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    //check if correct clip data of dragged view
                    return event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    //draw view when entered layout bounding box
                    v?.invalidate()
                    return true
                }
                DragEvent.ACTION_DRAG_LOCATION -> return true
                DragEvent.ACTION_DRAG_EXITED -> {
                    //call draw when bounding box of layout exited
                    v?.invalidate()
                    return true
                }
                DragEvent.ACTION_DROP -> {
                    //when dropped inside the layout
                    v?.invalidate()

                    val eventView = event.localState as View
                    val owner = eventView.parent as ViewGroup
                    owner.removeView(eventView)

                    event.clipData?.let { data ->
                        val priority = v!!.tag.toString()
                        val shiftTitle = data.getItemAt(0).text.toString()
                        viewModel.setPriority(shiftTitle, priority)
                    }

                    val destination = v as LinearLayout
                    destination.addView(eventView)
                    return true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    //always execute when drag ended
                    val draggedView = event.localState as View
                    draggedView.visibility = VISIBLE
                    v?.invalidate()
                    return true
                }
                else -> return false
            }
        }
        return false
    }
}
