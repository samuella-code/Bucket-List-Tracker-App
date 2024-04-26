package com.hfad.bucket_list_tracker_app_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hfad.bucket_list_tracker_app_2.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {

    // on below line we are creating a variable
    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding!!

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        homeBinding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // on below line we are initializing
        // all our variables
        //and setup menu host.
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner,Lifecycle.State.RESUMED)

        //initializing viewModel.
        taskViewModel = (activity as MainActivity).taskViewModel

        setUpHomeRecyclerView()

        // adding a click listener for fab button
        // to navigate to the add task fragment, to add goals.
        binding.addTaskFab.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addTaskFragment)
        }
    }

    private fun updateUI(task: List<Task>?){
        if (task != null){
            if (task.isNotEmpty()){
                binding.homeRecyclerView.visibility = View.VISIBLE

            }else {
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }

    private fun setUpHomeRecyclerView(){
        // on below line we are setting
        // adapter to our recycler view.
        taskAdapter = TaskAdapter()
        binding.homeRecyclerView.apply {
            //how we want the ui to de displayed.
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = taskAdapter
        }
        //we need to display all the task in the recyclerView for that we have to Observe.
        activity?.let {
            taskViewModel.getAllTasks().observe(viewLifecycleOwner){task ->
                taskAdapter.differ.submitList(task)
                updateUI(task)
            }
        }
    }

    //setting up menu on the action bar.
    private fun searchTask(query: String?){
        val searchQuery = "%$query"

        taskViewModel.searchTask(searchQuery).observe(this){list ->
            taskAdapter.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        if(newText != null){
            searchTask(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
      menu.clear()
        menuInflater.inflate(R.menu.menu,menu)
        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
     return false
    }


}