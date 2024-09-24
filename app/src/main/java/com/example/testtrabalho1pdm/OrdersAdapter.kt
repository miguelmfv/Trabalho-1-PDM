package com.example.testtrabalho1pdm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testtrabalho1pdm.model.Orders
import com.example.testtrabalho1pdm.OrdersAdapter.*

class OrdersAdapter(private val orderList: List<Orders>,
                    private val onOrderClick: (Orders) -> Unit
) : RecyclerView.Adapter<OrderViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_layout_orders, viewGroup, false)

        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        viewHolder.bind(order)
        viewHolder.itemView.setOnClickListener {
            onOrderClick(order)
        }
    }

    override fun getItemCount() = orderList.size

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val id: TextView = view.findViewById(R.id.orderID)
        private val dataPedido: TextView = view.findViewById(R.id.orderDataPedido)
        private val valorTot: TextView = view.findViewById(R.id.valorTot)
        private val obs: TextView = view.findViewById(R.id.observacao)

        fun bind(orders: Orders) {
            id.text = orders.id.toString()
            dataPedido.text = orders.dataPedido
            valorTot.text = orders.valorTot.toString()
            obs.text = orders.observacao
        }
    }

    fun getItem(id: Int): Orders {
        return orderList[id]
    }
}