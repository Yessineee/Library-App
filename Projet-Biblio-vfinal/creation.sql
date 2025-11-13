use biblio;
create table usr (
username varchar(20) primary KEY,
nom varchar(20),
prenom varchar(20),
mdp varchar(20),
dob date,
nlivre Integer,
finpenalty date
);

create table admn (
username varchar(20) primary KEY,
mdp varchar(20)
);

create table book(
id integer primary key auto_increment,
nom varchar(20),
author varchar(30),
genre varchar(20),
available bool
);

create table historique(
usr varchar(20) references usr(username) on delete cascade,
livre integer references book(id) on delete cascade,
datepret date,
dateretour  date
);


