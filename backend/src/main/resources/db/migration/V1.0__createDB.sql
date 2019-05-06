create sequence hibernate_sequence start with 1 increment by 1;

create table item (id bigint not null, category varchar(4096), description varchar(4096), title varchar(4096), primary key (id));
create table rank (id bigint not null, comment varchar(4096), score integer not null, item_id bigint not null, user_email varchar(255) not null, primary key (id));
create table user (email varchar(255) not null, enabled boolean not null, name varchar(255), password varchar(255), surname varchar(255), primary key (email));
create table user_roles (user_email varchar(255) not null, roles varchar(255));
alter table rank add constraint FKg8p3bu3hrfx6lmx13bdw7ioga foreign key (item_id) references item;
alter table rank add constraint FKf0qspyfc6ioywky72phq8osxt foreign key (user_email) references user;
alter table user_roles add constraint FKfinmcawb90mtj05cpco76b963 foreign key (user_email) references user;

