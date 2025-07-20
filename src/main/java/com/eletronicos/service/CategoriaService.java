package com.eletronicos.service;

import com.eletronicos.model.Categoria;
import com.eletronicos.model.CategoriaFormDTO; 
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
    public Categoria criar(CategoriaFormDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        categoria.setIcone(dto.getIcone());
        categoria.persist();
        return categoria;
    }

    @Transactional
    public Optional<Categoria> atualizar(Long id, CategoriaFormDTO dto) {
        Optional<Categoria> categoriaOpt = buscarPorId(id);
        
        if (categoriaOpt.isPresent()) {
            Categoria categoriaDb = categoriaOpt.get();
            categoriaDb.setNome(dto.getNome());
            categoriaDb.setDescricao(dto.getDescricao());
            categoriaDb.setIcone(dto.getIcone());
            // O método persist() não é necessário aqui, pois a entidade já está gerenciada
            return Optional.of(categoriaDb);
        }
        
        return Optional.empty();
    }

    @Transactional
    public boolean deletar(Long id) {
        return Categoria.deleteById(id);
    }
}
