create table estado(
id bigint not null auto_increment,
nome varchar(200) not null,
primary key (id)
)engine=InnoDB;

create table cidade(
id bigint not null auto_increment,
nome varchar(200) not null,
id_estado bigint not null,
primary key (id)
)engine=InnoDB;

alter table cidade add constraint fk_cidade_estado foreign key (id_estado) references estado (id);
