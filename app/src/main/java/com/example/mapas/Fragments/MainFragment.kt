package com.example.mapas.Fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.mapas.R
/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    var miVista: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        miVista = inflater.inflate(R.layout.fragment_main, container, false)
        return miVista
    }

}
