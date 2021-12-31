alter table produto add column restaurante_id bigint not null;


alter table produto add constraint fk_produto_restaurant foreign key (restaurante_id) references restaurante (id);