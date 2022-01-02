create table restaurante_usuario_responsavel(
id_usuario bigint not null,
id_restaurante bigint not null,
primary key(id_usuario,id_restaurante))engine=InnoDB;

alter table restaurante_usuario_responsavel add constraint fk_restaurante_usuario_responsavel_usuario 
foreign key (id_usuario) references usuario (id);

alter table restaurante_usuario_responsavel add constraint fk_restaurante_usuario_responsavel_restaurante 
foreign key (id_restaurante) references restaurante (id);
