package com.example.greenosapian.cart

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.greenosapian.R
import com.example.greenosapian.database.GreenDatabase
import com.example.greenosapian.database.Vegie
import com.example.greenosapian.databinding.FragmentCartBinding
import com.example.greenosapian.orderlist.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    protected lateinit var viewmodel: CartViewModel
    protected lateinit var adapter: VegieAdapter
    lateinit var orderPlacedDialog:AlertDialog

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
        binding.placeOrderButton.setOnClickListener{
            orderPlacedDialog.show()
        }

        binding.lifecycleOwner = this
        initRecyclerView()
        initDialog()
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

        //swipe to remove from cart
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true//To change body of created functions use File | Settings | File Templates.
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
//                Log.i("@TaskAdapter", "onSwiped : ${adapter.getItemFromAdapter(position)}")
//                viewModel.re(adapter.getItemFromAdapter(position))

                viewmodel.removeVeggieFromCart(adapter.getItemFromAdapter(position))
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recylerView)
    }

    //----------------------[INIT DIALOG]---------------
    private fun initDialog() {
        val builder = AlertDialog.Builder(this.context)
        val inflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_order_placed, null)

        builder.setView(view)
            .setPositiveButton(getString(R.string.go_to_menu)) { dialog, id ->
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToHomePageFragment(Firebase.auth.currentUser?.phoneNumber!!))
            }
        orderPlacedDialog = builder.create()
        orderPlacedDialog.setCancelable(false)
    }
}