package com.eletronicos.bo;

import com.eletronicos.formdto.ProdutoFormDTO;
import com.eletronicos.model.Categoria;
import com.eletronicos.model.Produto;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;

/**
 * BO (Business Object) para a entidade Produto.
 * Contém todas as regras de negócio relacionadas a produtos.
 */
@ApplicationScoped
public class ProdutoBO {

    /**
     * Valida os dados de um novo produto antes de o criar.
     * @param dto O DTO com os dados do formulário do produto.
     */
    public void validarNovoProduto(ProdutoFormDTO dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        if (dto.getPreco() == null) {
            throw new IllegalArgumentException("O preço do produto é obrigatório.");
        }
        if (dto.getIdCategoria() == null) {
            throw new IllegalArgumentException("A categoria do produto é obrigatória.");
        }

        // Regra de negócio: O stock não pode ser negativo
        if (dto.getEstoque() < 0) {
            throw new IllegalArgumentException("O stock não pode ser um valor negativo.");
        }
    }

    /**
     * Valida os dados de um produto a ser atualizado.
     * @param dto O DTO com os dados do formulário do produto.
     */
    public void validarAtualizacaoProduto(ProdutoFormDTO dto) {
        // Reutiliza as mesmas validações da criação
        validarNovoProduto(dto);
    }

    /**
     * Constrói uma entidade Produto a partir de um DTO e uma Categoria.
     * @param dto O DTO com os dados do formulário.
     * @return Uma nova entidade Produto pronta para ser persistida.
     */
    public Produto construirProduto(ProdutoFormDTO dto) {
        Categoria categoria = Categoria.findById(dto.getIdCategoria());
        if (categoria == null) {
            throw new WebApplicationException("Categoria com id " + dto.getIdCategoria() + " não encontrada.", 404);
        }

        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());
        produto.setImagem(dto.getImagem());
        produto.setMarca(dto.getMarca());
        produto.setModelo(dto.getModelo());
        produto.setDestaque(dto.isDestaque());
        produto.setCategoria(categoria);
        
        return produto;
    }
}
