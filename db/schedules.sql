create database schedules;

use schedules;

create table schedule (
  id int auto_increment,
  name varchar(30) not null,
  begin_at timestamp not null,
  end_at timestamp not null,
  primary key(id)
);

