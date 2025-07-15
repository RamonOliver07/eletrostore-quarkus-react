package com.eletronicos.service;

import com.eletronicos.model.Categoria;
import javax.enterprise.context.ApplicationScoped;
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

    public void salvar(Categoria categoria) {
        categoria.persist();
    }

    public Optional<Categoria> atualizar(Long id, Categoria categoria) {
        Optional<Categoria> categoriaOpt = buscarPorId(id);
        
        if (categoriaOpt.isPresent()) {
            Categoria categoriaDb = categoriaOpt.get();
            categoriaDb.setNome(categoria.getNome());
            categoriaDb.setDescricao(categoria.getDescricao());
            categoriaDb.setIcone(categoria.getIcone());
            categoriaDb.persist();
            return Optional.of(categoriaDb);
        }
        
        return Optional.empty();
    }

    public boolean deletar(Long id) {
        return Categoria.deleteById(id);
    }
}