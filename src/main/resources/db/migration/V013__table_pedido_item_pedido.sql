create table pedido(
id bigint not null auto_increment,
sub_total decimal,
taxa_frete decimal,
valor_total decimal,
status_pedido boolean,
data_criacao datetime,
data_confirmacao datetime,
data_cancelamento datetime,
data_entrega datetime,
id_forma_pagamento bigint not null,
id_restaurante bigint  not null,
id_cliente bigint  not null,
primary key (id))engine=InnoDB;

create table item_pedido(
id bigint auto_increment,
preco_unitario decimal,
preco_total decimal,
quantidade integer,
observacao varchar(255),
id_pedido bigint  not null,
id_produto bigint  not null,
primary key (id))engine=InnoDB;


alter table pedido add column id_item_pedido bigint;

alter table pedido add constraint fk_pedido_forma_pagamento foreign key (id_forma_pagamento) references
forma_pagamento (id);

alter table pedido add constraint fk_pedido_restaurante foreign key (id_restaurante) references
restaurante (id);

alter table pedido add constraint fk_pedido_cliente foreign key (id_cliente) references
usuario (id);

alter table item_pedido add constraint fk_item_pedido_pedido foreign key (id_pedido) references
pedido (id);

alter table item_pedido add constraint fk_item_pedido_produto foreign key (id_produto) references
produto (id);
