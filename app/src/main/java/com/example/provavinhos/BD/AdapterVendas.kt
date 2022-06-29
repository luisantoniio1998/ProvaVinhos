package com.example.provavinhos.BD

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.provavinhos.ListaVendasFragment
import com.example.provavinhos.R

class AdapterVendas(val fragment: ListaVendasFragment) : RecyclerView.Adapter<AdapterVendas.ViewHolderVenda>() {
    var cursor: Cursor? = null
        get() = field
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    var viewHolderSelecionado : ViewHolderVenda? = null

    inner class ViewHolderVenda(itemVenda: View) : RecyclerView.ViewHolder(itemVenda), View.OnClickListener {
        val textViewNomeCliente = itemVenda.findViewById<TextView>(R.id.textViewNomeCliente)
        val textViewNomeVinho = itemVenda.findViewById<TextView>(R.id.textViewNomeVinho)
        val textViewQuantidade = itemVenda.findViewById<TextView>(R.id.textViewQuantidade)
        val textViewPrecoGarrafa = itemVenda.findViewById<TextView>(R.id.textViewPrecoGarrafa)
        val textViewPreco = itemVenda.findViewById<TextView>(R.id.textViewPreco)

        init {
            itemVenda.setOnClickListener(this)
        }

        var venda : Sales? = null
            get() = field
            set(value: Sales?) {
                field = value

                textViewNomeCliente.text = venda?.nomeCliente ?: ""
                textViewNomeVinho.text = venda?.nomeVinho ?: ""
                textViewQuantidade.text = (venda?.quantidade ?: "").toString()
                textViewPrecoGarrafa.text = (venda?.precoGarrafa ?: "").toString()
                textViewPreco.text = (venda?.preco ?: "").toString()

            }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        override fun onClick(v: View?) {
            viewHolderSelecionado?.desseleciona()
            seleciona()
        }

        private fun seleciona() {
            itemView.setBackgroundResource(android.R.color.holo_orange_light)
            viewHolderSelecionado = this
            fragment.vendaSeleccionada = venda
        }

        private fun desseleciona() {
            itemView.setBackgroundResource(android.R.color.white)
        }
    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVenda {
        val itemVendas = fragment.layoutInflater.inflate(R.layout.item_venda, parent, false)
        return ViewHolderVenda(itemVendas)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolderVenda, position: Int) {
        cursor!!.moveToPosition(position)
        holder.venda = Sales.fromCursor(cursor!!)
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        if (cursor == null) return 0

        return cursor!!.count
    }
}