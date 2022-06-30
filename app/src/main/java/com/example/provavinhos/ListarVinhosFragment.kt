package com.example.provavinhos

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.provavinhos.BD.*
import com.example.provavinhos.databinding.FragmentListarVinhosBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

class ListarVinhosFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor>{

    var vinhoSelecionado :Wine? = null
        get() = field
        set(value) {
            field = value
            (requireActivity() as MainActivity).mostraOpcoesAlterarEliminar(field != null)
        }
    private var _binding: FragmentListarVinhosBinding? = null
    private var adapterVinhos: AdapterVinhos? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListarVinhosBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_VINHOS, null, this)

        adapterVinhos = AdapterVinhos(this)
        binding.recyclerViewVinhos.adapter = adapterVinhos
        binding.recyclerViewVinhos.layoutManager = LinearLayoutManager(requireContext())

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            ContentProviderClientes.ENDERECO_VINHOS,
            TabelaBDVinhos.TODAS_COLUNAS,
            null,
            null,
            "${TabelaBDVinhos.CAMPO_STOCK}"
        )

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterVinhos!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        if(_binding == null) return
        adapterVinhos!!.cursor = null
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_inserir -> {
                val acao = ListarVinhosFragmentDirections.actionListarVinhosFragmentToEditarVinhosFragment()
                findNavController().navigate(acao)
                (activity as MainActivity).atualizaTitulo(R.string.inserir_wine_label)
                true
            }

            else -> false
        }

    companion object{
        const val ID_LOADER_VINHOS = 1
    }
}
