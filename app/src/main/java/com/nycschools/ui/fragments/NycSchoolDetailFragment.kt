package com.nycschools.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.nycschools.databinding.FragmentNycSchoolDetailBinding
import com.nycschools.mvvm.MainViewModel
import com.nycschools.ui.state.UIState

class NycSchoolDetailFragment : Fragment() {

    private lateinit var binding: FragmentNycSchoolDetailBinding

    private val viewModel: MainViewModel by activityViewModels()

    val args: NycSchoolDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNycSchoolDetailBinding.inflate(inflater, container, false)

        viewModel.schoolDetailScores.observe(viewLifecycleOwner) {
            when(it) {
                is UIState.NycSchoolRecord -> {
                    binding.nycSchool = it.nycSchool
                } else -> {

                }
            }
        }

        viewModel.getSchoolRecord(args.schoolId)

        return binding.root
    }
}