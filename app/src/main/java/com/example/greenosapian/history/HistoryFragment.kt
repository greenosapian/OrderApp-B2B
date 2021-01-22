package com.example.greenosapian.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.greenosapian.R
import com.example.greenosapian.database.GreenDatabase
import com.example.greenosapian.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    protected lateinit var viewmodel: HistoryViewModel
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        val application = requireNotNull(this.activity).application
        val repository = HistoryRepository(GreenDatabase.getInstance(application).dao)
        val viewModelFactory =
            HistoryViewModelFactory(repository)

        viewmodel = ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java)

        setUpRecyclerView()
        setUpObservers()

        binding.viewmodel = viewmodel
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun setUpRecyclerView() {
        adapter = HistoryAdapter(object : HistoryListener {
        }
        )

        binding.recylerView.adapter = adapter
    }

    private fun setUpObservers() {
        viewmodel.historyList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }
}