package com.example.provavinhos

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.provavinhos.BD.Clients
import com.example.provavinhos.BD.ContentProviderClientes
import com.example.provavinhos.BD.TabelaBDClientes
import com.example.provavinhos.databinding.FragmentEditarClienteBinding
import com.google.android.material.snackbar.Snackbar

class EditarClienteFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarClienteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var cliente: Clients? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarClienteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_edicao

        if (arguments != null) {
            cliente = EditarClienteFragmentArgs.fromBundle(arguments!!).cliente

            if (cliente != null) {
                binding.editTextNome.setText(cliente!!.nome)
                binding.editTextTelemovel.setText(cliente!!.contacto)
                binding.editTextNif.setText(cliente!!.nif)
            }
        }

        LoaderManager.getInstance(this).initLoader(ID_LOADER_CLIENTES, null, this)
    }

    companion object {
        const val ID_LOADER_CLIENTES = 0
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaClientes()
                true
            }
            else -> false
        }

    private fun guardar() {
        val nome = binding.editTextNome.text.toString()
        if (nome.isBlank()) {
            binding.editTextNome.error = getString(R.string.campo_obrigatorio)
            binding.editTextNome.requestFocus()
            return
        }

        val contacto= binding.editTextTelemovel.text.toString()
        if (contacto.isBlank()) {
            binding.editTextTelemovel.error = getString(R.string.campo_obrigatorio)
            binding.editTextTelemovel.requestFocus()
            return
        }

        val nif = binding.editTextNif.text.toString()
        if (nif.isBlank()) {
            binding.editTextNif.error = getString(R.string.campo_obrigatorio)
            binding.editTextNif.requestFocus()
            return
        }


        val clienteGuardado =
            if (cliente== null) {
                insereCliente(nome, contacto , nif)
            } else {
                alteraCliente(nome, contacto, nif)
            }

        if (clienteGuardado) {
            Toast.makeText(requireContext(), R.string.done, Toast.LENGTH_LONG)
                .show()
            voltaListaClientes()
        } else {
            Snackbar.make(binding.editTextNome, R.string.erro, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }

    private fun alteraCliente(nome : String, contacto : String, nif : String ) : Boolean {
        val cliente = Clients(nome, contacto, nif )

        val enderecoClientes = Uri.withAppendedPath(ContentProviderClientes.ENDERECO_CLIENTES, "${this.cliente!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoClientes, cliente.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereCliente(nome : String, contacto : String, nif : String ): Boolean {
        val cliente = Clients(nome, contacto, nif)

        val enderecoClienteInserido = requireActivity().contentResolver.insert(ContentProviderClientes.ENDERECO_CLIENTES, cliente.toContentValues())

        return enderecoClienteInserido != null
    }

    private fun voltaListaClientes() {
        findNavController().navigate(R.id.action_editarClienteFragment_to_SecondFragment)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        TODO("Not yet implemented")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }
}