package com.nycschools.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nycschools.R
import com.nycschools.data.model.NycSchool
import com.nycschools.databinding.FragmentNycListBinding
import com.nycschools.ui.ListItemClickListener
import com.nycschools.mvvm.MainViewModel
import com.nycschools.ui.adapter.NycSchoolItemListAdapter
import com.nycschools.ui.state.UIState

class NycSchoolsListFragment : Fragment(), ListItemClickListener {

    private lateinit var binding: FragmentNycListBinding

    private val viewModel: MainViewModel by activityViewModels()

    private val listOfItems = ArrayList<NycSchool>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNycListBinding.inflate(layoutInflater, container, false)

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager

        viewModel.schoolsList.observe(viewLifecycleOwner) {
            when (it) {
                is UIState.Loading -> {
                    binding.progressBar.apply {
                        visibility = if (it.loading) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }
                    }
                }
                is UIState.NYCSchoolLists -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    listOfItems.clear()
                    listOfItems.addAll(it.listOfItems)
                    binding.recyclerView.adapter = NycSchoolItemListAdapter(listOfItems, this)
                }
                is UIState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.errorLayout.visibility = View.VISIBLE
                    binding.errorText.text =
                        it.exception?.message ?: getString(R.string.default_error_message)
                }
                else -> {}
            }
        }

        return binding.root
    }

    override fun onItemClick(schoolId: String) {
        findNavController().navigate(
            NycSchoolsListFragmentDirections.nycSchoolsListFragmentToNycSchoolDetailFragment(
                schoolId
            )
        )
    }
}