create table usuario_grupo(
id_usuario bigint not null,
id_grupo bigint not null,
primary key(id_usuario,id_grupo))engine=InnoDB;

alter table usuario_grupo add constraint fk_usuario_grupo_usuario foreign key (id_usuario) references
usuario (id);

alter table usuario_grupo add constraint fk_usuario_grupo_grupo foreign key (id_grupo)
references grupo (id);