alter table restaurante add column ativo boolean not null;

update restaurante set ativo = true where id= 1;