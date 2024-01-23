package com.albedo.testproject1.ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.albedo.testproject1.databinding.LoaderLayoutBinding

class LoaderLayoutFragment : Fragment(){

    companion object {
        const val TAG = "LoaderLayoutFragment"
    }

    private var _binding: LoaderLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = LoaderLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                //activity?.getFragmentManager()?.popBackStack();
            }
        }
        timer.start()
    }


}