package com.eletronicos.service;

import com.eletronicos.bo.CategoriaBO;
import com.eletronicos.formdto.CategoriaFormDTO;
import com.eletronicos.model.Categoria;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategoriaService {

    @Inject
    CategoriaBO categoriaBO; 

    public List<Categoria> listarTodas() {
        return Categoria.listAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return Optional.ofNullable(Categoria.findById(id));
    }

    @Transactional
    public Categoria criar(CategoriaFormDTO dto) {
        // 1. Delega a validação para o BO
        categoriaBO.validarNovaCategoria(dto);

        // 2. Delega a construção do objeto para o BO
        Categoria categoria = categoriaBO.construirCategoria(dto);

        // 3. O Service agora só se preocupa com a persistência
        categoria.persist();
        return categoria;
    }

    @Transactional
    public Optional<Categoria> atualizar(Long id, CategoriaFormDTO dto) {
        // 1. Delega a validação para o BO
        categoriaBO.validarNovaCategoria(dto); // Reutiliza a mesma validação

        Optional<Categoria> categoriaOpt = buscarPorId(id);
        
        if (categoriaOpt.isPresent()) {
            Categoria categoriaDb = categoriaOpt.get();
            
            // 2. Delega a atualização dos dados para o BO
            categoriaBO.atualizarDadosCategoria(categoriaDb, dto);
            
            return Optional.of(categoriaDb);
        }
        
        return Optional.empty();
    }

    @Transactional
    public boolean deletar(Long id) {
        return Categoria.deleteById(id);
    }
}
