package com.eletronicos.service;

import com.eletronicos.model.Categoria;
import com.eletronicos.model.Produto;
import com.eletronicos.model.ProdutoFormDTO; 

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProdutoService {

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
        // 1. Busca a Categoria pelo ID fornecido no DTO
        Categoria categoria = Categoria.findById(dto.getIdCategoria());
        if (categoria == null) {
            // Lança uma exceção se a categoria não for encontrada
            throw new WebApplicationException("Categoria com id " + dto.getIdCategoria() + " não encontrada.", 404);
        }

        // 2. Cria a nova entidade Produto a partir dos dados do DTO
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

        // 3. Persiste o novo produto no banco de dados
        produto.persist();
        return produto;
    }

    @Transactional
    public Optional<Produto> atualizar(Long id, ProdutoFormDTO dto) {
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
