package com.chandravir.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.chandravir.todolist.R
import com.chandravir.todolist.databinding.FragmentAddToDoPopupBinding
import com.chandravir.todolist.utils.ToDoData
import com.google.android.material.textfield.TextInputEditText

class AddToDoPopupFragment : DialogFragment() {

    private lateinit var binding: FragmentAddToDoPopupBinding
    private lateinit var listener: DialogNextBtnClickListener
    private var toDoData: ToDoData? = null

    fun setListener(listener: DialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddToDoPopupFragment"

        @JvmStatic
        fun newInstance(taskID: String, task: String) = AddToDoPopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskID)
                putString("task", task)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddToDoPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            toDoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString()
            )

            binding.todoEt.setText(toDoData?.task)

        }

        registerEvent()
    }

    private fun registerEvent() {
        binding.todoNextBtn.setOnClickListener {
            val toDoTask = binding.todoEt.text.toString()
            if (toDoTask.isNotEmpty()) {
                if(toDoData == null){
                    listener.onSaveTask(toDoTask, binding.todoEt)
                }
                else{
                    toDoData?.task = toDoTask
                    listener.onUpdateTask(toDoData!! , binding.todoEt)
                }

            } else {
                Toast.makeText(context, "Please add some task", Toast.LENGTH_SHORT).show()
            }
        }

        binding.todoClose.setOnClickListener {
            dismiss()
        }

    }

    interface DialogNextBtnClickListener {
        fun onSaveTask(todo: String, todoEt: TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData, todoEt: TextInputEditText)
    }


}