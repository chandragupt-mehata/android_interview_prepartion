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

class FragmentC: Fragment() {

    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = View.inflate(requireContext(), R.layout.fragment_c, null)
        view.findViewById<Button>(R.id.button_c).setOnClickListener {
            //requireActivity().supportFragmentManager.beginTransaction().add(R.id.fragment_container, FragmentC(), null).commit()
        }
        Log.d("#lifecycle", "onCreateView C")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("#lifecycle", "onActivityCreated C")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("#lifecycle", "onCreate C")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("#lifecycle", "onAttach C")
    }

    override fun onStart() {
        super.onStart()
        Log.d("#lifecycle", "onStart C")
    }

    override fun onPause() {
        super.onPause()
        Log.d("#lifecycle", "onPause C")
    }

    override fun onStop() {
        super.onStop()
        Log.d("#lifecycle", "onStop C")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("#lifecycle", "onViewStateRestored fragment C")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("#lifecycle", "onDestroyView C")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("#lifecycle", "onDestroy C")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("#lifecycle", "onDetach C")
    }

}