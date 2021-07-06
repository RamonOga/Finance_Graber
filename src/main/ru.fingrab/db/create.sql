create table price (
  id serial primary key,
  name varchar(255),
  highprice double precision,
  lowprice double precision,
  timestamp bigserial
);

create table company (
    id serial primary key,
    ticker varchar(255) NOT NULL UNIQUE,
    name varchar(255),
    sector varchar(255),
    industry varchar (255)
);