create table produto(
id bigint not null auto_increment,
nome varchar (255) not null,
comentario varchar (255) not null,
ativo boolean not null,
primary key(id))engine=InnoDB;

create table restaurante_produto(
id_restaurante bigint not null,
id_produto bigint not null,
primary key (id_restaurante,id_produto))engine=InnoDB;

alter table restaurante_produto add constraint fk_restaurante_produto_restaurante foreign key (id_restaurante)
references restaurante (id);

alter table restaurante_produto add constraint fk_restaurante_produto_produto foreign key (id_produto)
references produto (id);
