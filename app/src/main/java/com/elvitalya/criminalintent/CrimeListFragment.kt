package com.elvitalya.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elvitalya.criminalintent.databinding.FragmentCrimeListBinding

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {
    private var adapter: CrimeAdapter? = CrimeAdapter(emptyList())
    private lateinit var bind: FragmentCrimeListBinding
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentCrimeListBinding.inflate(layoutInflater, container, false)
        bind.crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        bind.crimeRecyclerView.adapter = adapter
        return bind.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer { crimes ->
                crimes?.let {
                    Log.i(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            })
    }

    private fun updateUI(crimes: List<Crime>) {
        adapter = CrimeAdapter(crimes)
        bind.crimeRecyclerView.adapter = adapter
    }

    companion object {

        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }

    }

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var crime: Crime
        val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)


        init {
            itemView.setOnClickListener(this)
        }
        fun binding(crime: Crime){
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = android.text.format.DateFormat.format("EEEE dd MMM yyyy, hh:mm", this.crime.date)

            solvedImageView.visibility = if(crime.isSolved){
                View.VISIBLE
            }else{
                View.GONE
            }
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()
        }
    }


    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            if (viewType == 1){
                val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
                return CrimeHolder(view)
            } else {
                val view = layoutInflater.inflate(R.layout.list_item_crime_police_required, parent, false)
                return CrimeHolder((view))
            }

        }

        override fun getItemViewType(position: Int): Int {
            return when(crimes[position].police_required){
                true -> 1
                else -> 2
            }
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.binding(crime)
        }

        override fun getItemCount(): Int = crimes.size

    }


}