package com.example.provavinhos.BD

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.provavinhos.ListarVinhosFragment
import com.example.provavinhos.R

class AdapterVinhos (val fragment: ListarVinhosFragment) : RecyclerView.Adapter<AdapterVinhos.ViewHolderVinho>() {
    var cursor: Cursor? = null
    get() = field
    set(value) {
        if(field != value){
            field = value
            notifyDataSetChanged()
        }
    }

    var viewHolderSelecionado : ViewHolderVinho? = null

    inner class ViewHolderVinho(itemVinho: View) : RecyclerView.ViewHolder(itemVinho), View.OnClickListener {
        val textViewNomeVinho = itemVinho.findViewById<TextView>(R.id.textViewVinhoNome)
        val textViewPrecoGarrafa= itemVinho.findViewById<TextView>(R.id.textViewPrecoGarrafa)
        val textViewNomeRegiao = itemVinho.findViewById<TextView>(R.id.textViewNomeRegiao)
        val textViewStock = itemVinho.findViewById<TextView>(R.id.textViewStock)

        var vinho : Wine? = null
            get() = field
        set(value: Wine?) {
            field = value

            textViewNomeVinho.text = vinho?.nomeVinho ?: ""
            textViewPrecoGarrafa.text = (vinho?.precoGarrafa?: "").toString()
            textViewNomeRegiao.text = vinho?.nomeRegiao ?: ""
            textViewStock.text = (vinho?.stock ?: "").toString()


        }

        override fun onClick(v: View?) {
            viewHolderSelecionado?.desseleciona()
            seleciona()
        }

        private fun seleciona() {
            itemView.setBackgroundResource(android.R.color.holo_orange_light)
            viewHolderSelecionado = this
            fragment.vinhoSelecionado = vinho
        }

        private fun desseleciona() {
            itemView.setBackgroundResource(android.R.color.white)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVinho {
        val itemVinho = fragment.layoutInflater.inflate(R.layout.item_vinho, parent, false)
        return ViewHolderVinho(itemVinho)
    }

    override fun onBindViewHolder(holder: ViewHolderVinho, position: Int) {
        cursor!!.moveToPosition(position)
        holder.vinho = Wine.fromCursor(cursor!!)
    }

    override fun getItemCount(): Int {
       if(cursor == null) return 0

        return cursor!!.count
    }


}