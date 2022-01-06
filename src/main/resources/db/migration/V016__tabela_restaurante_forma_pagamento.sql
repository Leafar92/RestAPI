create table restaurante_forma_pagamento(
restaurante_id bigint not null,
forma_pagamento_id bigint not null,
primary key (restaurante_id, forma_pagamento_id))engine=InnoDB;

alter table restaurante_forma_pagamento add constraint fk_restaurante_forma_pagamento_restaurante foreign key (restaurante_id)
references restaurante (id);

alter table restaurante_forma_pagamento add constraint fk_restaurante_forma_pagamento_forma_pagamento 
foreign key (forma_pagamento_id)
references forma_pagamento (id);