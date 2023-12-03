package com.example.vinyls_jetpack_application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.vinyls_jetpack_application.R
import com.example.vinyls_jetpack_application.databinding.LoginCollectorFragmentBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class LoginCollectorFragment: Fragment() {
    private var _bindning: LoginCollectorFragmentBinding? = null
    private val binding get() = _bindning!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindning = LoginCollectorFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonCollector = view.findViewById<Button>(R.id.button_add_album)
        buttonCollector.setOnClickListener {
            findNavController().navigate(R.id.action_login_collector_fragment_to_albumFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindning = null
    }

}