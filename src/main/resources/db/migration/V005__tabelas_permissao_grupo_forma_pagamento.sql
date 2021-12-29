create table forma_pagamento(
id bigint not null auto_increment,
nome varchar(255),
primary key (id))engine=InnoDB;


create table grupo(
id bigint not null auto_increment,
nome varchar(255),
primary key (id))engine=InnoDB;

create table permissao(
id bigint not null auto_increment,
nome varchar(255),
descricao varchar(255),
primary key (id))engine=InnoDB;

create table grupo_permissao(
grupo_id bigint not null,
permissao_id bigint not null,
descricao varchar(255),
primary key (grupo_id, permissao_id))engine=InnoDB;

alter table grupo_permissao add constraint fk_grupo_permissao_grupo foreign key (grupo_id) references grupo (id);
alter table grupo_permissao add constraint fk_grupo_permissao_permissao foreign key (permissao_id) references 
permissao (id);
