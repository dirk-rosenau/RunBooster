package com.dr.drillinstructor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dr.drillinstructor.R
import com.dr.drillinstructor.databinding.FragmentInTrainingBinding
import com.dr.drillinstructor.ui.events.StopClicked
import com.dr.drillinstructor.ui.events.TrainingEvent
import com.dr.drillinstructor.ui.vm.InTrainingFragmentViewModel
import com.dr.drillinstructor.ui.vm.MainActivityViewModel
import com.dr.drillinstructor.util.TrainingStateProvider
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InTrainingFragment : Fragment() {

    private val viewModel: InTrainingFragmentViewModel by viewModel()
    private val mainActivityViewModel: MainActivityViewModel by sharedViewModel()
    private val trainingStateProvider: TrainingStateProvider by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_in_training, container, false)
        val binding = FragmentInTrainingBinding.bind(view)
        binding.lifecycleOwner = this
        binding.vm = viewModel
        observeEvents()
        return view
    }

    private fun observeEvents() {
        viewModel.events.observe(viewLifecycleOwner, Observer(::handleEvent))
        trainingStateProvider.liveTrainingState.observe(
            viewLifecycleOwner,
            Observer { state -> viewModel.setTrainingState(state) })
    }

    private fun handleEvent(event: TrainingEvent) {
        when (event) {
            StopClicked -> handleStopClick()
            else -> {
            }
        }
    }

    private fun handleStopClick() {
        mainActivityViewModel.stopButtonClicked()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            InTrainingFragment()
    }
}