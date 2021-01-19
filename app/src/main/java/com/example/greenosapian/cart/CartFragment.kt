package com.example.greenosapian.cart

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
import com.example.greenosapian.database.Vegie
import com.example.greenosapian.databinding.FragmentCartBinding
import com.example.greenosapian.orderlist.*

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var viewmodel: CartViewModel
    private lateinit var adapter: VegieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)

        val application = requireNotNull(this.activity).application
        val repository = CartRepository(GreenDatabase.getInstance(application).dao)
        val viewModelFactory =
            CartViewModelFactory(repository)

        viewmodel = ViewModelProvider(this, viewModelFactory).get(CartViewModel::class.java)


        binding.viewmodel = viewmodel

        viewmodel.cartItemList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.lifecycleOwner = this
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        adapter = VegieAdapter(
            VegieListener(
                //add Listener
                {
                    viewmodel.addVeggieInCart(it)
                },
                //remove Listener
                {
                    viewmodel.removeVeggieFromCart(it)
                },
                //change Quantity
                { vegie: Vegie, step: Int ->
                    viewmodel.changeVeggieQuantity(vegie, step)
                }
            )
        )

        binding.recylerView.adapter = adapter
    }

}