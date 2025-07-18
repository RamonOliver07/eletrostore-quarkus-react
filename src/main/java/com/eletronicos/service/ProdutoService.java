package com.eletronicos.service;

import com.eletronicos.model.Categoria;
import com.eletronicos.model.Produto;
import javax.enterprise.context.ApplicationScoped;
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

    public List<Produto> buscarPorCategoria(Long categoriaId) {
        return Produto.list("categoria.id", categoriaId);
    }

    public List<Produto> buscarPorNome(String nome) {
        return Produto.list("nome like ?1", "%" + nome + "%");
    }

    public List<Produto> listarDestaques() {
        return Produto.list("destaque", true);
    }

    public void salvar(Produto produto) {
        produto.persist();
    }

    public Optional<Produto> atualizar(Long id, Produto produto) {
        Optional<Produto> produtoOpt = buscarPorId(id);
        
        if (produtoOpt.isPresent()) {
            Produto produtoDb = produtoOpt.get();
            produtoDb.setNome(produto.getNome());
            produtoDb.setDescricao(produto.getDescricao());
            produtoDb.setPreco(produto.getPreco());
            produtoDb.setEstoque(produto.getEstoque());
            produtoDb.setImagem(produto.getImagem());
            produtoDb.setMarca(produto.getMarca());
            produtoDb.setModelo(produto.getModelo());
            produtoDb.setDestaque(produto.getDestaque());
            
            // Atualizar categoria se fornecida
            if (produto.getCategoria() != null && produto.getCategoria().id != null) {
                Categoria categoria = Categoria.findById(produto.getCategoria().id);
                if (categoria != null) {
                    produtoDb.setCategoria(categoria);
                }
            }
            
            produtoDb.persist();
            return Optional.of(produtoDb);
        }
        
        return Optional.empty();
    }

    public boolean deletar(Long id) {
        return Produto.deleteById(id);
    }

	public void criar(Produto produto) {
		
	}
}