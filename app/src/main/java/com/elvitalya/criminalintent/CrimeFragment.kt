package com.elvitalya.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elvitalya.criminalintent.databinding.FragmentCrimeBinding

class CrimeFragment : Fragment() {
    private lateinit var bind: FragmentCrimeBinding
    private lateinit var crime: Crime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentCrimeBinding.inflate(layoutInflater, container, false)
//        val view = inflater.inflate(R.layout.fragment_crime, container, false)
        bind.crimeDate.apply {
            text = crime.date.toString()
            isEnabled = false
        }
        bind.crimeSolved.apply {
            setOnCheckedChangeListener { _, isChecked ->
                crime.isSolved = isChecked
            }
        }
        return bind.root
    }

    override fun onStart() {
        super.onStart()
        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crime.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        bind.crimeTitle.addTextChangedListener(titleWatcher)
    }
}