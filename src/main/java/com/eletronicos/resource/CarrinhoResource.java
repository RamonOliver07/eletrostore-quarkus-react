package com.eletronicos.resource;

import com.eletronicos.model.Produto;
import com.eletronicos.service.ProdutoService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/api/carrinho")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarrinhoResource {

    @Inject
    ProdutoService produtoService;

    @POST
    @Path("/calcular")
    public Response calcularCarrinho(List<ItemCarrinho> itens) {
        BigDecimal total = BigDecimal.ZERO;
        List<ItemCarrinhoResponse> itensResponse = new ArrayList<>();
        
        for (ItemCarrinho item : itens) {
            Optional<Produto> produtoOpt = produtoService.buscarPorId(item.produtoId);
            
            if (produtoOpt.isEmpty()) {
                // Retorna o erro em formato JSON
                return Response.status(Response.Status.BAD_REQUEST)
                                .entity(Map.of("erro", "Produto com ID " + item.produtoId + " não foi encontrado. Por favor, remova-o do carrinho."))
                                .build();
            }
            
            Produto produto = produtoOpt.get();

            if (produto.getEstoque() < item.quantidade) {
                // Retorna o erro em formato JSON
                return Response.status(Response.Status.BAD_REQUEST)
                                .entity(Map.of("erro", "Estoque insuficiente para o produto: " + produto.getNome()))
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
        
        BigDecimal frete = calcularFrete(total);
        BigDecimal totalComFrete = total.add(frete);
        
        Map<String, Object> response = new HashMap<>();
        response.put("itens", itensResponse);
        response.put("subtotal", total);
        response.put("frete", frete);
        response.put("total", totalComFrete);
        
        return Response.ok(response).build();
    }
    
    private BigDecimal calcularFrete(BigDecimal subtotal) {
        if (subtotal.compareTo(BigDecimal.valueOf(300)) > 0) {
            return BigDecimal.ZERO;
        } else {
            return BigDecimal.valueOf(25.90);
        }
    }
    
    // DTO para receber os dados do frontend
    public static class ItemCarrinho {
        public Long produtoId;
        public int quantidade;
    }
    
    // DTO para enviar a resposta para o frontend
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
}
