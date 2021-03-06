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
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.christophprenissl.shiftify.R
import com.christophprenissl.shiftify.databinding.CardShiftBinding
import com.christophprenissl.shiftify.databinding.FragmentPriorityBinding
import com.christophprenissl.shiftify.model.entity.PlanElement
import com.christophprenissl.shiftify.util.*
import com.christophprenissl.shiftify.viewmodel.nurse.NurseViewModel
import com.google.android.material.card.MaterialCardView

class PriorityFragment : Fragment(), View.OnLongClickListener, OnDragListener {

    private val viewModel: NurseViewModel by activityViewModels()
    private lateinit var binding: FragmentPriorityBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            createPriorityBinding(viewModel.aboutToSavePlanElement.value!!, inflater, container)
        navController = findNavController()

        val observer = Observer<PlanElement?> {
            binding.title.text = getString(R.string.priority_title, it.date.dayMonthYearString())
        }
        viewModel.aboutToSavePlanElement.observe(viewLifecycleOwner, observer)

        binding.priorityFirstPlace.setOnDragListener(this)
        binding.prioritySecondPlace.setOnDragListener(this)
        binding.priorityThirdPlace.setOnDragListener(this)
        binding.priorityFourthPlace.setOnDragListener(this)

        binding.cancelButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.okButton.setOnClickListener {
            viewModel.saveChosenPlanElementInMonth()
            requireActivity().onBackPressed()
        }

        binding.okNextButton.isInvisible = viewModel.checkIfLastDayOfCalendarView()

        binding.okNextButton.setOnClickListener {
            if (viewModel.findAndSetNextPlanElement()) {
                navController.navigate(R.id.action_priorityFragment_self)
            }
        }

        return binding.root
    }

    private fun createPriorityBinding(planElement: PlanElement, inflater: LayoutInflater, container: ViewGroup?): FragmentPriorityBinding {
        val binding = FragmentPriorityBinding.inflate(inflater, container, false)
        planElement.priorityMap.forEach { element ->
            val shiftCardBinding = CardShiftBinding.inflate(inflater, container, false)
            shiftCardBinding.shiftTitle.text = element.key
            shiftCardBinding.root.tag = element.key
            context?.let { context ->
                when(element.key) {
                    EARLY_SHIFT_NAME ->  shiftCardBinding.root.setBackgroundColor(context.getColor(R.color.shift_early))
                    LATE_SHIFT_NAME -> {
                        shiftCardBinding.shiftTitle.setTextColor(context.getColor(R.color.shift_font_light_color))
                        shiftCardBinding.root.setBackgroundColor(context.getColor(R.color.shift_late))
                    }
                    NIGHT_SHIFT_NAME -> {
                        shiftCardBinding.root.setBackgroundColor(context.getColor(R.color.shift_night))
                        shiftCardBinding.shiftTitle.setTextColor(context.getColor(R.color.shift_font_light_color))
                    }
                    HOLIDAY_SHIFT_NAME -> shiftCardBinding.root.setBackgroundColor(context.getColor(R.color.shift_holiday))
                    FREE_SHIFT_NAME -> shiftCardBinding.root.setBackgroundColor(context.getColor(R.color.shift_free))
                }
                when(element.value) {
                    WISH_SHIFT_PRIORITY -> binding.priorityFirstPlace.addView(shiftCardBinding.root)
                    HIGH_PRIORITY -> binding.prioritySecondPlace.addView(shiftCardBinding.root)
                    NEUTRAL_PRIORITY -> binding.priorityThirdPlace.addView(shiftCardBinding.root)
                    NEGATIVE_PRIORITY -> binding.priorityFourthPlace.addView(shiftCardBinding.root)
                }
                shiftCardBinding.root.setOnLongClickListener(this)
            }
        }
        return binding
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

                    val eventView = event.localState as MaterialCardView
                    val owner = eventView.parent as ViewGroup
                    owner.removeView(eventView)

                    event.clipData?.let { data ->
                        val priority = v!!.tag.toString()
                        val shiftTitle = data.getItemAt(0).text.toString()
                        viewModel.setShiftAndPriority(shiftTitle, priority)
                    }

                    val destination = v as LinearLayout
                    destination.addView(eventView)
                    return true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    //always execute when drag ended
                    val draggedView = event.localState as MaterialCardView
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
