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
import com.christophprenissl.shiftify.databinding.FragmentPriorityBinding
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
            binding.title.text = viewModel.chosenDay.value?.get(Calendar.DAY_OF_MONTH).toString()
        }
        viewModel.chosenDay.observe(viewLifecycleOwner, observer)

        binding.earlyShiftCard.setOnLongClickListener(this)
        binding.priorityFirstPlace.setOnDragListener(this)
        binding.prioritySecondPlace.setOnDragListener(this)
        binding.priorityThirdPlace.setOnDragListener(this)
        binding.priorityFourthPlace.setOnDragListener(this)

        return binding.root
    }

    override fun onLongClick(v: View?): Boolean {
        val clipText = "This is text"
        val item = ClipData.Item(clipText)
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val data = ClipData(clipText, mimeTypes, item)

        v?.let {
            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            it.visibility = View.INVISIBLE
        }

        return true
    }

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        //handle drag events
        event?.let {
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
