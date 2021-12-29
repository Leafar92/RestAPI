create table usuario(
id bigint not null auto_increment,
nome varchar(255),
email varchar(255),
senha varchar(255),
data_cadastro datetime,
primary key (id))engine=InnoDB;