drop DATABASE if EXISTS scolarite_iris_2026_2a; 
CREATE DATABASE scolarite_iris_2026_2a ;
use scolarite_iris_2026_2a; 

create table classe (
	idclasse int(5) not null auto_increment, 
	nom varchar(50), 
	salle varchar(50),
	diplome varchar(50),
	PRIMARY KEY(idclasse)
);
create table etudiant (
	idetudiant int(5) not null auto_increment, 
	nom varchar(50), 
	prenom varchar(50),
	email varchar(50),
	tel varchar(20),
	adresse varchar(50),
	idclasse int(5) not null,
	PRIMARY KEY(idetudiant),
	FOREIGN KEY(idclasse) REFERENCES classe (idclasse)
);
create table professeur (
	idprofesseur int(5) not null auto_increment, 
	nom varchar(50), 
	prenom varchar(50),
	email varchar(50),
	diplome varchar(50),
	PRIMARY KEY(idprofesseur)
);
create table matiere (
	idmatiere int(5) not null auto_increment, 
	nom varchar(50), 
	coeff int(2),
	nbheures int(5),
	idclasse int(5) not null,
	idprofesseur int(5) not null,
	PRIMARY KEY(idmatiere),
	FOREIGN KEY(idclasse) REFERENCES classe (idclasse),
	FOREIGN KEY(idprofesseur) REFERENCES professeur (idprofesseur)
);

create table user (
	iduser int (3) not null auto_increment, 
	nom varchar(50), 
	prenom varchar(50), 
	email varchar(50), 
	mdp varchar(255), 
	primary key (iduser)
);

insert into user values (null, "Bryan", "Marcus", "a@gmail.com", "123"); 
insert into user values (null, "Chaima", "Khadijatou", 
"b@gmail.com", "456"); 


 