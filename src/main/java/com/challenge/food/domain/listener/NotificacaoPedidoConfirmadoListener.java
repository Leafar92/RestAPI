package com.challenge.food.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.challenge.food.domain.event.PedidoCanceladoEvent;
import com.challenge.food.domain.model.Pedido;
import com.challenge.food.domain.service.EmailService;

@Component
public class NotificacaoPedidoConfirmadoListener {

	@Autowired
	private EmailService emailService;
	
	@TransactionalEventListener
	public void aoPedidoCancelado(PedidoCanceladoEvent event) {
		Pedido pedido = event.getPedido();
		
		var email = EmailService.Email.builder()
				.titulo("Pedido Cancelado" + pedido.getRestaurante().getNome())
				.variavel("pedido", pedido).corpo("email-pedido-cancelado.html")
				.destinatario(pedido.getCliente().getEmail()).build();

		emailService.enviar(email);
	}

}
