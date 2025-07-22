package com.eletronicos.resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.eletronicos.model.Produto;

@Path("/api/carrinho")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarrinhoResource {

    @POST
    @Path("/calcular")
    @PermitAll
    public Response calcularCarrinho(List<ItemCarrinho> itens) {
        BigDecimal total = BigDecimal.ZERO;
        List<ItemCarrinhoResponse> itensResponse = new ArrayList<>();
        
        for (ItemCarrinho item : itens) {
            Produto produto = Produto.findById(item.produtoId);
            
            if (produto == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Produto não encontrado: " + item.produtoId)
                        .build();
            }
            
            if (produto.getEstoque() < item.quantidade) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Estoque insuficiente para o produto: " + produto.getNome())
                        .build();
            }
            
            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(item.quantidade));
            total = total.add(subtotal);
            
            itensResponse.add(new ItemCarrinhoResponse(
                produto.id,
                produto.getNome(),
                produto.getImagem(),
                produto.getPreco(),
                item.quantidade,
                subtotal
            ));
        }
        
        // Calcular frete simulado baseado no total do pedido
        BigDecimal frete = calcularFrete(total);
        BigDecimal totalComFrete = total.add(frete);
        
        Map<String, Object> response = new HashMap<>();
        response.put("itens", itensResponse);
        response.put("subtotal", total);
        response.put("frete", frete);
        response.put("total", totalComFrete);
        
        return Response.ok(response).build();
    }
    
    @POST
    @Path("/verificar-estoque")
    @PermitAll
    @Transactional
    public Response verificarEstoque(List<ItemCarrinho> itens) {
        List<ProdutoEstoqueResponse> produtosComProblema = new ArrayList<>();
        
        for (ItemCarrinho item : itens) {
            Produto produto = Produto.findById(item.produtoId);
            
            if (produto == null) {
                produtosComProblema.add(new ProdutoEstoqueResponse(
                    item.produtoId,
                    null,
                    0,
                    item.quantidade,
                    "Produto não encontrado"
                ));
                continue;
            }
            
            if (produto.getEstoque() < item.quantidade) {
                produtosComProblema.add(new ProdutoEstoqueResponse(
                    produto.id,
                    produto.getNome(),
                    produto.getEstoque(),
                    item.quantidade,
                    "Estoque insuficiente"
                ));
            }
        }
        
        if (!produtosComProblema.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "PROBLEMAS_ESTOQUE");
            response.put("produtos", produtosComProblema);
            return Response.status(Response.Status.CONFLICT).entity(response).build();
        }
        
        return Response.ok(Map.of("status", "OK")).build();
    }
    
    private BigDecimal calcularFrete(BigDecimal subtotal) {
        // Simulação simples de cálculo de frete
        if (subtotal.compareTo(BigDecimal.valueOf(300)) > 0) {
            return BigDecimal.ZERO; // Frete grátis para compras acima de 300
        } else {
            return BigDecimal.valueOf(25.90); // Frete fixo
        }
    }
    
    public static class ItemCarrinho {
        public Long produtoId;
        public int quantidade;
    }
    
    public static class ItemCarrinhoResponse {
        public Long produtoId;
        public String nome;
        public String imagem;
        public BigDecimal preco;
        public int quantidade;
        public BigDecimal subtotal;
        
        public ItemCarrinhoResponse(Long produtoId, String nome, String imagem,
                                     BigDecimal preco, int quantidade, BigDecimal subtotal) {
            this.produtoId = produtoId;
            this.nome = nome;
            this.imagem = imagem;
            this.preco = preco;
            this.quantidade = quantidade;
            this.subtotal = subtotal;
        }
    }
    
    public static class ProdutoEstoqueResponse {
        public Long produtoId;
        public String nome;
        public int estoqueDisponivel;
        public int quantidadeSolicitada;
        public String mensagem;
        
        public ProdutoEstoqueResponse(Long produtoId, String nome, int estoqueDisponivel,
                                       int quantidadeSolicitada, String mensagem) {
            this.produtoId = produtoId;
            this.nome = nome;
            this.estoqueDisponivel = estoqueDisponivel;
            this.quantidadeSolicitada = quantidadeSolicitada;
            this.mensagem = mensagem;
        }
    }
}