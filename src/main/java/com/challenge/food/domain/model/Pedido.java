package com.challenge.food.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.challenge.food.domain.event.PedidoCanceladoEvent;
import com.challenge.food.domain.event.PedidoConfirmadoEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String codigo;

	@Column(name = "sub_total")
	private BigDecimal subtotal = BigDecimal.ZERO;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal = BigDecimal.ZERO;;

	@Embedded
	private Endereco enderecoEntrega;

	@Enumerated(EnumType.STRING)
	private StatusPedido statusPedido = StatusPedido.CRIADO;

	@CreationTimestamp
	private OffsetDateTime dataCriacao;

	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;

	@ManyToOne
	@JoinColumn(nullable = false, name = "id_forma_pagamento")
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(nullable = false, name = "id_restaurante")
	private Restaurante restaurante;

	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();

	public void calcularTotalPedido() {
		Double totalProdutos = this.getItens().stream()
				.collect(Collectors.summingDouble(i -> i.getPrecoTotal().doubleValue()));
		this.subtotal = subtotal.add(new BigDecimal(totalProdutos));
		this.valorTotal = subtotal.add(taxaFrete);
	}

	@PrePersist
	private void prePersist() {
		this.dataCriacao = OffsetDateTime.now();
		createUuid();
	}

	public void confirmar() {
		this.statusPedido = StatusPedido.CONFIRMADO;
		this.dataConfirmacao = OffsetDateTime.now();
		registerEvent(new PedidoConfirmadoEvent(this));
	}
	
	public void cancelar() {
		this.statusPedido = StatusPedido.CANCELADO;
		this.dataCancelamento = OffsetDateTime.now();
		registerEvent(new PedidoCanceladoEvent(this));
	}

	private void createUuid() {
		setCodigo(UUID.randomUUID().toString());
	}
}
