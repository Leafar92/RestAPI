package com.challenge.food.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.challenge.food.domain.event.PedidoConfirmadoEvent;
import com.challenge.food.domain.model.Pedido;
import com.challenge.food.domain.service.EmailService;

@Component
public class NotificacaoPedidoCanceladoListener {

	@Autowired
	private EmailService emailService;
	
	@TransactionalEventListener
	public void aoPedidoConfirmado(PedidoConfirmadoEvent event) {
		Pedido pedido = event.getPedido();
		
		var email = EmailService.Email.builder()
				.titulo("<strong> Pedido Confirmado" + pedido.getRestaurante().getNome() + " </strong>")
				.variavel("pedido", pedido).corpo("email-pedido-confirmado.html")
				.destinatario(pedido.getCliente().getEmail()).build();

		emailService.enviar(email);
	}

}
