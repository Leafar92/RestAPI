alter table pedido add column endereco_cep bigint;
alter table pedido add column endereco_rua varchar(255);
alter table pedido add column endereco_numero integer;
alter table pedido add column cidade_id bigint;

alter table pedido add constraint fk_pedido_cidade foreign key (cidade_id) references cidade (id);