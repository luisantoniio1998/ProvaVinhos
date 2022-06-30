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
import com.example.provavinhos.BD.ContentProviderClientes
import com.example.provavinhos.BD.TabelaBDRegiao
import com.example.provavinhos.BD.Wine
import com.example.provavinhos.databinding.FragmentEditarVinhoBinding
import com.google.android.material.snackbar.Snackbar

class EditarVinhoFragment: Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentEditarVinhoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var vinho: Wine? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarVinhoBinding.inflate(inflater, container, false)
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
            vinho = EditarVinhoFragmentArgs.fromBundle(arguments!!).vinho

            if (vinho != null) {
                binding.editTextNomeVinho.setText(vinho!!.nomeVinho)
                binding.editTextStock.setText((vinho!!.stock).toString())
                binding.editTextPrecoGarrafa.setText((vinho!!.precoGarrafa).toString())

            }
        }

        LoaderManager.getInstance(this).initLoader(ID_LOADER_REGIOES, null, this)
    }

    companion object {
        const val ID_LOADER_REGIOES = 0
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
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            ContentProviderClientes.ENDERECO_REGIOES,
            TabelaBDRegiao.TODAS_COLUNAS,
            null,
            null,
            "${TabelaBDRegiao.CAMPO_NOME}"
        )

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is *not* allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See [ FragmentManager.openTransaction()][androidx.fragment.app.FragmentManager.beginTransaction] for further discussion on this.
     *
     *
     * This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     *
     *  *
     *
     *The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a [android.database.Cursor]
     * and you place it in a [android.widget.CursorAdapter], use
     * the [android.widget.CursorAdapter.CursorAdapter] constructor *without* passing
     * in either [android.widget.CursorAdapter.FLAG_AUTO_REQUERY]
     * or [android.widget.CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER]
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     *  *  The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a [android.database.Cursor] from a [android.content.CursorLoader],
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * [android.widget.CursorAdapter], you should use the
     * [android.widget.CursorAdapter.swapCursor]
     * method so that the old Cursor is not closed.
     *
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        val adapterRegioes = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaBDRegiao.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )

        binding.spinnerRegiao.adapter = adapterRegioes

        atualizaRegiaoSelecionada()
    }

    private fun atualizaRegiaoSelecionada() {
        if (vinho == null) return
        val idCategoria = vinho!!.categoria.id

        val ultimaCategoria = binding.spinnerCategorias.count - 1

        for (i in 0..ultimaCategoria) {
            if (binding.spinnerCategorias.getItemIdAtPosition(i) == idCategoria) {
                binding.spinnerCategorias.setSelection(i)
                return
            }
        }
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     *
     * This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (_binding == null) return
        binding.spinnerCategorias.adapter = null
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean =
        when(item.itemId) {
            R.id.action_guardar -> {
                guardar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaLivros()
                true
            }
            else -> false
        }

    private fun guardar() {
        val titulo = binding.editTextTitulo.text.toString()
        if (titulo.isBlank()) {
            binding.editTextTitulo.error = getString(R.string.titulo_obrigatorio)
            binding.editTextTitulo.requestFocus()
            return
        }

        val autor = binding.editTextAutor.text.toString()
        if (autor.isBlank()) {
            binding.editTextAutor.error = getString(R.string.autor_obrigatorio)
            binding.editTextAutor.requestFocus()
            return
        }

        val idCategoria = binding.spinnerCategorias.selectedItemId
        if (idCategoria == Spinner.INVALID_ROW_ID) {
            binding.textViewCategoria.error = getString(R.string.categoria_obrigatoria)
            binding.spinnerCategorias.requestFocus()
            return
        }

        val livroGuardado =
            if (livro == null) {
                insereLivro(titulo, autor, idCategoria)
            } else {
                alteraLivro(titulo, autor, idCategoria)
            }

        if (livroGuardado) {
            Toast.makeText(requireContext(), R.string.livro_guardado_sucesso, Toast.LENGTH_LONG)
                .show()
            voltaListaLivros()
        } else {
            Snackbar.make(binding.editTextTitulo, R.string.erro_guardar_livro, Snackbar.LENGTH_INDEFINITE).show()
            return
        }
    }

    private fun alteraLivro(titulo: String, autor: String, idCategoria: Long) : Boolean {
        val livro = Livro(titulo, autor, Categoria(id = idCategoria))

        val enderecoLivro = Uri.withAppendedPath(ContentProviderLivros.ENDERECO_LIVROS, "${this.livro!!.id}")

        val registosAlterados = requireActivity().contentResolver.update(enderecoLivro, livro.toContentValues(), null, null)

        return registosAlterados == 1
    }

    private fun insereLivro(titulo: String, autor: String, idCategoria: Long): Boolean {
        val livro = Livro(titulo, autor, Categoria(id = idCategoria))

        val enderecoLivroInserido = requireActivity().contentResolver.insert(ContentProviderLivros.ENDERECO_LIVROS, livro.toContentValues())

        return enderecoLivroInserido != null
    }

    private fun voltaListaLivros() {
        findNavController().navigate(R.id.action_editar_livro_to_lista_livros)
    }
}