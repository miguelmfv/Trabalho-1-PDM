package com.example.testtrabalho1pdm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testtrabalho1pdm.ProductAdapter.*
import com.example.testtrabalho1pdm.model.Products

class ProductAdapter(private val productList: List<Products>,
                     private val onProductClick: (Products) -> Unit
) : RecyclerView.Adapter<ProductsViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_layout_products, viewGroup, false)

        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ProductsViewHolder, position: Int) {
        val product = productList[position]
        viewHolder.bind(product)
        viewHolder.itemView.setOnClickListener {
            onProductClick(product)
        }
    }

    override fun getItemCount() = productList.size

    class ProductsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val id: TextView = view.findViewById(R.id.productID)
        private val nome: TextView = view.findViewById(R.id.nomeProducts)
        private val descricao: TextView = view.findViewById(R.id.descricao)
        private val preco: TextView = view.findViewById(R.id.preco)
        private val estoque: TextView = view.findViewById(R.id.estoque)

        fun bind(product: Products) {
            id.text = product.id.toString()
            nome.text = product.nome
            descricao.text = product.descricao
            preco.text = product.preco.toString()
            estoque.text = product.estoque.toString()
        }
    }

    fun getItem(id: Int): Products {
        return productList[id]
    }
}