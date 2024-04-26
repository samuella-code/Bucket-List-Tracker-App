package com.hfad.bucket_list_tracker_app_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.hfad.bucket_list_tracker_app_2.databinding.FragmentEditTaskBinding

class EditTaskFragment : Fragment(R.layout.fragment_edit_task), MenuProvider {

    // on below line we are creating variables.
    private var editTaskBinding: FragmentEditTaskBinding? = null
    private val binding get() = editTaskBinding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var currentTask:Task
    private val args: EditTaskFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        editTaskBinding = FragmentEditTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // on below line we are initializing
        // all our variables
        //and setup menu host.
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        //initializing viewModel
        taskViewModel = (activity as MainActivity).taskViewModel
        currentTask = args.task!!

        //used to update.
        binding.editTaskTitle.setText(currentTask.taskTitle)
        binding.editTaskDesc.setText(currentTask.taskContent)

        // adding a click listener for fab button of how to edit the task
        // to navigate to the add task fragment, to add goals.
        binding.editTaskFab.setOnClickListener {
            val taskTitle = binding.editTaskTitle.text.toString().trim()
            val taskContent = binding.editTaskDesc.text.toString().trim()


            //if the inputs are empty it throws a toast and doesn't save until the user adds an input.
            if (taskTitle.isNotEmpty()){
                val task = Task(currentTask.id, taskTitle, taskContent)
                taskViewModel.updateTask(task)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }else {
                Toast.makeText(context,"Please enter Task Title", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //delete function for deleting the task or goal and when deleted it navigates to te home fragment.
    private fun deleteTask(){
            taskViewModel.deleteTask(currentTask)
        Toast.makeText(context,"Task Deleted", Toast.LENGTH_SHORT).show()
        view?.findNavController()?.popBackStack(R.id.homeFragment,false)
       }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_task,menu)
    }

    //setting up menu on the action bar
    // and the icon for how the task will be deleted from local storage.
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
     return when(menuItem.itemId){
         R.id.deleteMenu -> {
             deleteTask()
             true
         } else -> false
     }
    }

    override fun onDestroy() {
        super.onDestroy()
        editTaskBinding = null
    }


}