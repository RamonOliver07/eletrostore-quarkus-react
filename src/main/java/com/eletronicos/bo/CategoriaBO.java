package com.eletronicos.bo;

import com.eletronicos.model.Categoria;
import com.eletronicos.formdto.CategoriaFormDTO;

import javax.enterprise.context.ApplicationScoped;

/**
 * BO (Business Object) para a entidade Categoria.
 * Contém todas as regras de negócio relacionadas a categorias.
 */
@ApplicationScoped
public class CategoriaBO {

    /**
     * Valida os dados de uma nova categoria antes de a criar.
     * @param dto O DTO com os dados do formulário da categoria.
     */
    public void validarNovaCategoria(CategoriaFormDTO dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome da categoria é obrigatório.");
        }
    }

    /**
     * Constrói uma entidade Categoria a partir de um DTO.
     * @param dto O DTO com os dados do formulário.
     * @return Uma nova entidade Categoria pronta para ser persistida.
     */
    public Categoria construirCategoria(CategoriaFormDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        categoria.setIcone(dto.getIcone());
        return categoria;
    }

    /**
     * Atualiza os dados de uma entidade Categoria existente com base num DTO.
     * @param categoria A entidade Categoria a ser atualizada.
     * @param dto O DTO com os novos dados.
     */
    public void atualizarDadosCategoria(Categoria categoria, CategoriaFormDTO dto) {
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        categoria.setIcone(dto.getIcone());
    }
}
