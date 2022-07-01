package com.example.provavinhos

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.provavinhos.BD.ContentProviderClientes
import com.example.provavinhos.BD.Wine
import com.example.provavinhos.databinding.FragmentEliminarVinhoBinding
import com.google.android.material.snackbar.Snackbar

class EliminarVinhoFragment : Fragment() {
    private var _binding: FragmentEliminarVinhoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var vinho: Wine
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarVinhoBinding.inflate(inflater, container, false)
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

        vinho = EliminarVinhoFragmentArgs.fromBundle(arguments!!).vinho

        binding.textViewVinho.text = vinho.nomeVinho
        binding.textViewprecoGarrafa.text = (vinho.precoGarrafa).toString()
        binding.textViewstock.text = (vinho.stock).toString()
        binding.textViewRegiaoVinho.text = vinho.regiao.nomeRegiao

    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_eliminar -> {
                eliminaVinho()
                true
            }
            R.id.action_cancelar -> {
                voltaListaLivros()
                true
            }
            else -> false
        }

    private fun eliminaVinho() {
        val alertDialog = AlertDialog.Builder(requireContext())

        alertDialog.apply {
            setTitle(R.string.delete_wine)
            setMessage(R.string.sure)
            setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialogInterface, i ->  })
            setPositiveButton(R.string.delete, DialogInterface.OnClickListener { dialogInterface, i -> confirmaEliminarVinho() })
            show()
        }
    }

    private fun confirmaEliminarVinho() {
        val enderecoVinho = Uri.withAppendedPath(ContentProviderClientes.ENDERECO_VINHOS, "${vinho.id}")
        val registosEliminados = requireActivity().contentResolver.delete(enderecoVinho, null, null)

        if (registosEliminados != 1) {
            Snackbar.make(
                binding.textViewVinho,
                R.string.erro,
                Snackbar.LENGTH_INDEFINITE
            ).show()
            return
        }

        Toast.makeText(requireContext(), R.string.done, Toast.LENGTH_LONG).show()
        voltaListaLivros()
    }

    private fun voltaListaLivros() {
        val acao = EliminarVinhoFragmentDirections.actionEliminarVinhoFragmentToListarVinhosFragment()
        findNavController().navigate(acao)
    }
}