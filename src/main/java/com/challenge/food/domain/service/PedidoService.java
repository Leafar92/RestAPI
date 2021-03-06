package com.challenge.food.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.food.domain.exception.PedidoNaoEncontradoException;
import com.challenge.food.domain.model.Cidade;
import com.challenge.food.domain.model.FormaPagamento;
import com.challenge.food.domain.model.ItemPedido;
import com.challenge.food.domain.model.Pedido;
import com.challenge.food.domain.model.Produto;
import com.challenge.food.domain.model.Restaurante;
import com.challenge.food.domain.model.Usuario;
import com.challenge.food.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRespository;
	
	@Autowired
	private RestauranteService restauranteService;
	

	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private UsuarioService usuarioService;

	public Pedido findByCodigoOrThrowException(String codigo) {
		return pedidoRespository.findByCodigo(codigo).orElseThrow(() -> new PedidoNaoEncontradoException(codigo));
	}

	public Pedido save(Pedido pedido) {
		Restaurante restaurante = validarPedido(pedido);
		
		List<ItemPedido> itens = validarItem(pedido, restaurante);
		
		pedido.setTaxaFrete(restaurante.getTaxaFrete());
		calcularItensPedido(itens);
		pedido.setItens(itens);
		
		pedido.calcularTotalPedido();
		
		return pedidoRespository.save(pedido);
		
	}

	private List<ItemPedido> validarItem(Pedido pedido, Restaurante restaurante) {
		List<ItemPedido> itens = pedido.getItens();
		
		
		itens.forEach(item -> {
			Produto produto = verificarProduto(item, restaurante.getId());
			item.setProduto(produto);
		});
		return itens;
	}

	private Restaurante validarPedido(Pedido pedido) {
		Usuario cliente = usuarioService.findByIdOrThrowException(pedido.getCliente().getId());
		pedido.setCliente(cliente);
		
		Cidade cidade = cidadeService.findById(pedido.getEnderecoEntrega().getCidade().getId());
		pedido.getEnderecoEntrega().setCidade(cidade);
		
		Restaurante restaurante = restauranteService.findById(pedido.getRestaurante().getId());
		pedido.setRestaurante(restaurante);
		
		FormaPagamento formaPagamento = formaPagamentoService.findByIdOrThrowException(pedido.getFormaPagamento().getId());
		restauranteService.getFormaPagamentoOrThrowsException(restaurante, formaPagamento);
		pedido.setFormaPagamento(formaPagamento);
		return restaurante;
	}
	
	public void calcularItensPedido(List<ItemPedido> itens) {
		itens.forEach(i -> i.setPrecoUnitario(i.getProduto().getPreco()));
		itens.forEach(ItemPedido::calcularItens);
	}
	
	
	public Produto verificarProduto(ItemPedido item, Long idRestaurante) {
		return produtoService.findByRestauranteAndId(item.getProduto().getId(), idRestaurante);
	}
	
}
