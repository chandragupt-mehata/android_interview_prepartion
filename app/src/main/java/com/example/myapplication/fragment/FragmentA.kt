package com.example.myapplication.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.viewmodel.MainViewModel

/**
 * https://medium.com/androiddevelopers/the-android-lifecycle-cheat-sheet-part-iii-fragments-afc87d4f37fd
 * https://vinodpattanshetti49.medium.com/fragment-lifecycle-while-doing-add-and-replace-6a3f084364af
 * https://androidlearnersite.wordpress.com/2017/02/27/fragment-lifecycle-during-fragment-transaction/ (youc an cross check with log traces)
 */
class FragmentA: Fragment() {

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = View.inflate(requireContext(), R.layout.fragment_layout_a, null)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        Log.d("#viewmodel", "#viewmodel onCreate fragment: $viewModel")
        view.findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.increaseCounter()
            //requireActivity().supportFragmentManager.beginTransaction().add(R.id.fragment_container, FragmentB(), null).commit()
        }
        Log.d("#lifecycle", "onCreateView A")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("#lifecycle", "onActivityCreated A")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("#lifecycle", "onCreate A")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("#lifecycle", "onAttach A")
    }

    override fun onStart() {
        super.onStart()
        Log.d("#lifecycle", "onStart A")
    }

    override fun onPause() {
        super.onPause()
        Log.d("#lifecycle", "onPause A")
    }

    override fun onStop() {
        super.onStop()
        Log.d("#lifecycle", "onStop A")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("#lifecycle", "onViewStateRestored fragment A")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("#lifecycle", "onDestroyView A")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("#lifecycle", "onDestroy A")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("#lifecycle", "onDetach A")
    }

}