package com.eletronicos.service;

import com.eletronicos.bo.ProdutoBO;
import com.eletronicos.formdto.ProdutoFormDTO;
import com.eletronicos.model.Categoria;
import com.eletronicos.model.Produto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProdutoService {

    @Inject
    ProdutoBO produtoBO; // Injeta o nosso novo Business Object

    public List<Produto> listarTodos() {
        return Produto.listAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(Produto.findById(id));
    }

    public List<Produto> listarDestaques() {
        return Produto.list("destaque", true);
    }

    @Transactional
    public Produto criar(ProdutoFormDTO dto) {
        // 1. Delega a validação para o BO
        produtoBO.validarNovoProduto(dto);

        // 2. Delega a construção do objeto para o BO
        Produto produto = produtoBO.construirProduto(dto);

        // 3. O Service agora só se preocupa com a persistência
        produto.persist();
        return produto;
    }

    @Transactional
    public Optional<Produto> atualizar(Long id, ProdutoFormDTO dto) {
        // 1. Delega a validação para o BO
        produtoBO.validarAtualizacaoProduto(dto);

        Optional<Produto> produtoOpt = buscarPorId(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();

            // Busca a Categoria pelo ID
            Categoria categoria = Categoria.findById(dto.getIdCategoria());
            if (categoria == null) {
                throw new WebApplicationException("Categoria com id " + dto.getIdCategoria() + " não encontrada.", 404);
            }

            // Atualiza os campos do produto com os dados do DTO
            produto.setNome(dto.getNome());
            produto.setDescricao(dto.getDescricao());
            produto.setPreco(dto.getPreco());
            produto.setEstoque(dto.getEstoque());
            produto.setImagem(dto.getImagem());
            produto.setMarca(dto.getMarca());
            produto.setModelo(dto.getModelo());
            produto.setDestaque(dto.isDestaque());
            produto.setCategoria(categoria);
            
            return Optional.of(produto);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deletar(Long id) {
        return Produto.deleteById(id);
    }
}
