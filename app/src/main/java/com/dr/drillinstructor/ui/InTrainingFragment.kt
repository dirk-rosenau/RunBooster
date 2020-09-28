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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InTrainingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InTrainingFragment : Fragment() {

    lateinit var binding: FragmentInTrainingBinding
    private val viewModel: InTrainingFragmentViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_in_training, container, false)
        binding = FragmentInTrainingBinding.bind(view)
        binding.vm = viewModel
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            InTrainingFragment()
    }
}