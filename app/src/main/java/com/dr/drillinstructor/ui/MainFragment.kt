package com.dr.drillinstructor.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.dr.drillinstructor.R
import com.dr.drillinstructor.databinding.FragmentMainBinding
import com.dr.drillinstructor.ui.vm.MainActivityViewModel
import com.dr.drillinstructor.ui.vm.MainFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainFragmentViewModel by viewModel()
    private val mainActivityViewModel: MainActivityViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val binding = FragmentMainBinding.bind(view)
        binding.lifecycleOwner = this
        binding.vm = viewModel

        viewModel.playButtonClicked.observe(
            viewLifecycleOwner,
            Observer { handlePlayButtonClick() })
        viewModel.settingsButtonClicked.observe(
            viewLifecycleOwner,
            Observer { handleSettingsButtonClicked() })

        return view
    }

    private fun handleSettingsButtonClicked() {
        Log.d("MainFragment", "settings clicked")
        mainActivityViewModel.settingsButtonClicked()
    }

    private fun handlePlayButtonClick() {
        Log.d("MainFragment", "playClicked")
        mainActivityViewModel.playButtonClicked()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}