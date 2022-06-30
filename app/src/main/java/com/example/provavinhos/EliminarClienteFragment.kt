package com.example.provavinhos

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.provavinhos.BD.Clients
import com.example.provavinhos.BD.ContentProviderClientes
import com.example.provavinhos.databinding.FragmentEliminarClienteBinding
import com.google.android.material.snackbar.Snackbar

class EliminarClienteFragment : Fragment() {
    private var _binding: FragmentEliminarClienteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var cliente: Clients

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarClienteBinding.inflate(inflater, container, false)
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
        activity.idMenuAtual = R.menu.menu_eliminar

        cliente = EliminarClienteFragmentArgs.fromBundle(arguments!!).cliente

        binding.textViewNome.text = cliente.nome
        binding.textViewContacto.text = cliente.contacto
        binding.textViewNIF.text = cliente.nif
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                eliminaCliente()
                true
            }
            R.id.action_cancelar -> {
                voltaListaLivros()
                true
            }
            else -> false
        }

    private fun eliminaCliente() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.delete_client)
            setMessage(R.string.sure)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.delete, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarCliente() })
            show()
        }
    }

    private fun confirmaEliminarCliente() {
        val enderecoCliente = Uri.withAppendedPath(ContentProviderClientes.ENDERECO_CLIENTES, "${cliente.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoCliente, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewNome,
                R.string.erro,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.done, Toast.LENGTH_LONG).show()
        voltaListaLivros()
    }

    private fun voltaListaLivros() {
        val acao = EliminarClienteFragmentDirections.actionEliminarClienteFragmentToSecondFragment()
        findNavController().navigate(acao)
    }
}