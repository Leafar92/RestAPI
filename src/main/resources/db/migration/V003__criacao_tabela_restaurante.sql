create table restaurante(
id bigint not null auto_increment,
nome varchar(200) not null,
taxa_frete decimal,
cozinha_id bigint not null,
endereco_rua varchar(100),
endereco_numero varchar(50),
endereco_cep bigint,
cidade_id bigint,
primary key (id)
)engine=InnoDB;

alter table restaurante add constraint restaurante_cozinha foreign key (cozinha_id) references cozinha (id);
alter table restaurante add constraint restaurante_cidade foreign key (cidade_id) references cidade (id);


