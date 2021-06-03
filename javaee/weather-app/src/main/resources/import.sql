-- create table City (id varchar(255) not null, humidity integer, maxTemp integer, minTemp integer,  temp integer, tempFeelsLike integer, weatherType varchar(255), wind integer, primary key (id))
-- create table Country (id varchar(255) not null, name varchar(255), primary key (id))
-- create table Country_City (Country_id varchar(255)not null, cities_id varchar(255) not null)

insert into City(id, name, weatherType, temp, maxTemp, minTemp, tempFeelsLike, wind) values ('lon','London', 'sunny', 9, 11, 7, 9, 3);
insert into City(id, name, weatherType, temp, maxTemp, minTemp, tempFeelsLike, wind) values ('man','Manchester', 'rainy-7', 7, 8, 5, 3, 10);
insert into City(id, name, weatherType, temp, maxTemp, minTemp, tempFeelsLike, wind) values ('edi','Edinburgh', 'snowy-4', -7, -6, -9, -13, 7);

insert into City(id, name, weatherType, temp, maxTemp, minTemp, tempFeelsLike, wind) values ('nyc','New York', 'sunny', 9, 11, 7, 9, 3);
insert into City(id, name, weatherType, temp, maxTemp, minTemp, tempFeelsLike, wind) values ('chi','Chicago', 'rainy-7', 7, 8, 5, 3, 10);
insert into City(id, name, weatherType, temp, maxTemp, minTemp, tempFeelsLike, wind) values ('sfo','San Francisco', 'snowy-4', -7, -6, -9, -13, 7);
insert into City(id, name, weatherType, temp, maxTemp, minTemp, tempFeelsLike, wind) values ('ral','Raleigh', 'snowy-4', -7, -6, -9, -13, 7);

insert into City(id, name, weatherType, temp, maxTemp, minTemp, tempFeelsLike, wind) values ('par','Paris', 'snowy-4', -7, -6, -9, -13, 7);
insert into City(id, name, weatherType, temp, maxTemp, minTemp, tempFeelsLike, wind) values ('can','Cannes', 'snowy-4', -7, -6, -9, -13, 7);

insert into Country (id, name) values ('en', 'England');
insert into Country (id, name) values ('us', 'United States of America');
insert into Country (id, name) values ('fr', 'France');

insert into Country_City (Country_id, cities_id) values ('en','lon');
insert into Country_City (Country_id, cities_id) values ('en','man');
insert into Country_City (Country_id, cities_id) values ('en','edi');

insert into Country_City (Country_id, cities_id) values ('us','nyc');
insert into Country_City (Country_id, cities_id) values ('us','chi');
insert into Country_City (Country_id, cities_id) values ('us','sfo');
insert into Country_City (Country_id, cities_id) values ('us','ral');

insert into Country_City (Country_id, cities_id) values ('fr','par');
insert into Country_City (Country_id, cities_id) values ('fr','can');
