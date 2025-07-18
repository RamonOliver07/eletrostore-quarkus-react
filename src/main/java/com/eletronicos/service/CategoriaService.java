package com.eletronicos.service;

import com.eletronicos.model.Categoria;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategoriaService {

    public List<Categoria> listarTodas() {
        return Categoria.listAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return Optional.ofNullable(Categoria.findById(id));
    }

    @Transactional
    public void criar(Categoria categoria) { // <-- Renomeado de "salvar" para "criar"
        categoria.persist();
    }

    @Transactional
    public Optional<Categoria> atualizar(Long id, Categoria categoria) {
        Optional<Categoria> categoriaOpt = buscarPorId(id);
        
        if (categoriaOpt.isPresent()) {
            Categoria categoriaDb = categoriaOpt.get();
            categoriaDb.setNome(categoria.getNome());
            categoriaDb.setDescricao(categoria.getDescricao());
            categoriaDb.setIcone(categoria.getIcone());
            return Optional.of(categoriaDb);
        }
        
        return Optional.empty();
    }

    @Transactional
    public boolean deletar(Long id) {
        return Categoria.deleteById(id);
    }
}
