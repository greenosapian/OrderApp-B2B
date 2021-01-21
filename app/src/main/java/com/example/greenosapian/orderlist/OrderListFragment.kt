package com.example.greenosapian.orderlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.greenosapian.R
import com.example.greenosapian.database.GreenDatabase
import com.example.greenosapian.database.Vegie
import com.example.greenosapian.databinding.FragmentOrderListBinding

class OrderListFragment : Fragment() {

    private lateinit var binding: FragmentOrderListBinding
    private lateinit var viewmodel: OrderListViewModel
    private lateinit var adapter: VegieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_list, container, false)

        val application = requireNotNull(this.activity).application
        val repository = OrderRepository(GreenDatabase.getInstance(application).dao)
        val viewModelFactory =
            OrderListViewModelFactory(repository)

        viewmodel = ViewModelProvider(this, viewModelFactory).get(OrderListViewModel::class.java)


        binding.viewmodel = viewmodel

        binding.lifecycleOwner = this
        initRecyclerView()

        binding.proceedToCartButton.setOnClickListener {
            findNavController().navigate(OrderListFragmentDirections.actionOrderListFragmentToCartFragment())
        }

        viewmodel.veggies.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        return binding.root
    }

    private fun initRecyclerView() {
        adapter = VegieAdapter(object : VegieListener {
            override fun onAddClicked(vegie: Vegie) {
                viewmodel.addVeggieInCart(vegie)
            }

            override fun onRemovedClick(vegie: Vegie) {
                viewmodel.removeVeggieFromCart(vegie)
            }

            override fun onQuantityChanged(vegie: Vegie, step: Int) {
                viewmodel.changeVeggieQuantity(vegie, step)
            }
        }
        )

        binding.recylerView.adapter = adapter
    }
}