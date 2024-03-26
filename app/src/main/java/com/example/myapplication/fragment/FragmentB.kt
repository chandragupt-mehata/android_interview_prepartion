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
import com.example.myapplication.R

class FragmentB: Fragment() {

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = View.inflate(requireContext(), R.layout.fragment_b, null)
        view.findViewById<Button>(R.id.button_b).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, FragmentC(), null).addToBackStack(null).commit()
        }
        Log.d("#lifecycle", "onCreateView B")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("#lifecycle", "onActivityCreated B")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("#lifecycle", "onCreate B")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("#lifecycle", "onAttach B")
    }

    override fun onStart() {
        super.onStart()
        Log.d("#lifecycle", "onStart B")
    }

    override fun onPause() {
        super.onPause()
        Log.d("#lifecycle", "onPause B")
    }

    override fun onStop() {
        super.onStop()
        Log.d("#lifecycle", "onStop B")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("#lifecycle", "onViewStateRestored fragment B")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("#lifecycle", "onDestroyView B")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("#lifecycle", "onDestroy B")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("#lifecycle", "onDetach B")
    }

}