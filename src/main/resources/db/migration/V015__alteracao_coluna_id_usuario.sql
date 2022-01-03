alter table pedido add column usuario_cliente_id bigint not null;

alter table pedido add constraint fk_pedido_usuario foreign key (usuario_cliente_id) references usuario (id);