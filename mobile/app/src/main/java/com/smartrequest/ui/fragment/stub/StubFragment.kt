package com.smartrequest.ui.fragment.stub


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.smartrequest.R

/**
 * A simple [Fragment] subclass.
 * Use the [StubFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StubFragment : Fragment() {
	
	companion object {
		/**
		 * Use this factory method to create a new instance of this fragment
		 * @return A new instance of fragment StubFragment.
		 */
		@JvmStatic
		fun newInstance() = StubFragment()
	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_stub, container, false)
	}
	
}
