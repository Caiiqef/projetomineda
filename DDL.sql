create schema bancodedados;
use bancodedados;
-- create user 'caiiqef'@'localhost' identified by '12345';
grant select, insert, delete, update on bancodedados.* to caiiqef@'localhost';

create table cliente(
cliente_id bigint unsigned not null auto_increment,
cliente_nome varchar(20) not null,
cliente_email varchar(50) not null,
cliente_senha varchar(100) not null,
primary key (cliente_id),
unique key uni_cliente_nome (cliente_nome),
unique key uni_cliente_email (cliente_email)
);

create table pedido(
  pedido_id bigint unsigned auto_increment,
  pedido_desc varchar(50) not null,
  pedido_price float not null,  
  primary key (pedido_id),
  unique key uni_pedido_desc (pedido_desc)  
);

create table cliente_pedido(
  cliente_id bigint unsigned not null,
  pedido_id bigint unsigned not null,
  primary key (cliente_id, pedido_id),
  foreign key pedcli_cli_fk (cliente_id) references cliente (cliente_id) on delete restrict on update cascade,
  foreign key pedcli_ped_fk (pedido_id) references pedido (pedido_id) on delete restrict on update cascade
);

create table autorizacao (
    autorizacao_id bigint unsigned not null auto_increment,
    autorizacao_nome varchar(20) not null,
    primary key (autorizacao_id),
    unique key uni_autorizacao_nome (autorizacao_nome)
);

create table uau_usuario_autorizacao (
    cliente_id bigint unsigned not null,
    autorizacao_id bigint unsigned not null,
    primary key (cliente_id, autorizacao_id),
    foreign key uau_usr_fk (cliente_id) references cliente (cliente_id) on delete restrict on update cascade,
    foreign key uau_aut_fk (autorizacao_id) references autorizacao (autorizacao_id) on delete restrict on update cascade
);

create table cau_cliente_autorizacao(
	cliente_id bigint unsigned not null,
    autorizacao_id bigint unsigned not null,
    primary key (cliente_id, autorizacao_id),
    foreign key cau_cliente_fk (cliente_id) references cliente (cliente_id) on delete restrict on update cascade,
    foreign key cau_autorizacao_fk (autorizacao_id) references autorizacao (autorizacao_id) on delete restrict on update cascade
);

insert into cliente values (1, 'Caique', 'caiquefernandes@gmail.com', '$2a$08$NTU2NjY4MzM5NWU1OTc4YOYih1txxdBJecHg4FvXE9ltTJWbqWCaS');
insert into cliente values (2, 'Rafael', 'rafaelfernandes@gmail.com', '$2a$08$NTU2NjY4MzM5NWU1OTc4YOYih1txxdBJecHg4FvXE9ltTJWbqWCaS');

insert into pedido values (1, "000001", 39.99);
insert into pedido values (2, "000002", 50.52);
insert into pedido values (3, "000003", 9.98);
insert into pedido values (4, "000004", 75.23);

insert into cliente_pedido values (1, 4);
insert into cliente_pedido values (2, 2);
insert into cliente_pedido values (1, 1);

insert into autorizacao values (1, 'ROLE_ADMIN');
insert into autorizacao values (2, 'ROLE_USER');

-- Cliente autorização
insert into cau_cliente_autorizacao values (1,2);
insert into cau_cliente_autorizacao values (2,1);

select * from cliente;
select * from pedido;
select * from cau_cliente_autorizacao;
select * from autorizacao;


-- delete from uau_usuario_autorizacao where cli_id > 3;
-- delete from aut_autorizacao where aut_id > 2;
-- delete from cliente where cli_id > 3;
