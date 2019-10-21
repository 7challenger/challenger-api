create table Users (
  id serial not null primary key,
  username varchar(100) not null,
  password varchar(256) not null,
  project_id int,
  foreign key (project_id) references Projects(id)
);
