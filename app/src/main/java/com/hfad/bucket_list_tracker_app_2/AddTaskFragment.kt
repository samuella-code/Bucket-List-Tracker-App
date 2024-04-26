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
import com.hfad.bucket_list_tracker_app_2.databinding.FragmentAddTaskBinding

class AddTaskFragment : Fragment(R.layout.fragment_add_task), MenuProvider {

    // on below line we are creating a variable
    private var addTaskBinding: FragmentAddTaskBinding? = null
    private val binding get() = addTaskBinding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var addTaskView: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        addTaskBinding = FragmentAddTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // on below line we are initializing
        // all our variables
        //and setup menu host.
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        //initializing viewModel
        //and also initialise the view it self.
        taskViewModel = (activity as MainActivity).taskViewModel
        addTaskView = view
    }

    //function as to how the task or goal will be created with the input and
    // navigate back to the home fragment and if the input are not in it it will not save.
    private fun saveTask(view: View){
        val taskTitle = binding.addTaskTitle.text.toString().trim()
        val taskContent = binding.addTaskDesc.text.toString().trim()

        if (taskTitle.isNotEmpty()){
            val task = Task(0, taskTitle, taskContent)
            taskViewModel.addTask(task)

            Toast.makeText(addTaskView.context,"Task Saved", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)
        }else{
            Toast.makeText(addTaskView.context, "Please enter Task Title",Toast.LENGTH_SHORT).show()
        }
    }

    //setting up menu on the action bar
    // and the icon for how the task will be add and saved.
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_task,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
       return when(menuItem.itemId){
           R.id.saveMenu ->{
               saveTask(addTaskView)
               true
           }
           else -> false
       }
    }

    override fun onDestroy() {
        super.onDestroy()
        addTaskBinding = null
    }

}