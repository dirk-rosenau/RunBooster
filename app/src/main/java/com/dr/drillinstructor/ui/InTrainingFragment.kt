package com.dr.drillinstructor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dr.drillinstructor.R
import com.dr.drillinstructor.databinding.FragmentInTrainingBinding
import com.dr.drillinstructor.ui.vm.InTrainingFragmentViewModel
import org.koin.android.ext.android.inject

class InTrainingFragment : Fragment() {

    lateinit var binding: FragmentInTrainingBinding
    private val viewModel: InTrainingFragmentViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_in_training, container, false)
        binding = FragmentInTrainingBinding.bind(view)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            InTrainingFragment()
    }
}