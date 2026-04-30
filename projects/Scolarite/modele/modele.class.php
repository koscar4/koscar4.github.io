<?php 
	class Modele {
		private $unPdo;
		
		public function __construct () {
			$url  = "mysql:host=localhost:8889;dbname=scolarite_iris_2026_2a";
			$user = "root";
			$mdp  = "root" ;
			try{
				$this-> unPdo = new PDO ($url, $user, $mdp);
				
			}
			catch (PDOException $exp) {
				echo "<br> erreur d'execution a url :".$url;
				echo $exp->getMessage ();
			}
		}
		/************ Gestion des classes **************/
		public function insert_classe($tab){
			$requete="insert into classe values (null, :nom, :salle, :diplome);";
			$donnees =array(	":nom"=>$tab ['nom'],                  
								":salle"=>$tab ['salle'],                     
								":diplome"=>$tab ['diplome'] ); 
			$exec= $this->unPdo->prepare ($requete);
			$exec-> execute($donnees); 
			}

		public function selectAll_classes(){
			$requete="select * from classe ; ";
			$exec= $this->unPdo->prepare ($requete);
			$exec-> execute(); 
			return $exec->fetchAll(); //retourner toutes les classes
			}

		public function selectLike_classes ($filtre){
			$requete="select * from classe where nom like :filtre or salle like :filtre or diplome like :filtre ; ";
			
			$donnees = array(":filtre"=>"%".$filtre."%"); 
		
			$exec= $this->unPdo->prepare ($requete);
			$exec-> execute($donnees); 
			return $exec->fetchAll();  
		}

		public function delete_classe ($idclasse){
			$requete ="delete from classe where idclasse = :idclasse;";
			$exec= $this->unPdo->prepare ($requete);
			$donnees =array(":idclasse"=>$idclasse); 
			$exec-> execute($donnees); 
		}

		public function update_classe ($tab){
			$requete ="update classe set nom = :nom, salle = :salle, diplome = :diplome where idclasse = :idclasse;";
			$exec= $this->unPdo->prepare ($requete);
			$donnees =array(":idclasse"=>$tab['idclasse'], 
							":nom"=>$tab ['nom'],                  
							":salle"=>$tab ['salle'],      
							":diplome"=>$tab ['diplome'] 
							); 
			$exec-> execute($donnees); 
		}

		public function selectWhere_classe ($idclasse){
			//récupère une classe identifiée par idclasse. 
			$requete ="select * from classe where idclasse = :idclasse;"; 
			$exec= $this->unPdo->prepare ($requete);
			$donnees =array(":idclasse"=>$idclasse); 
			$exec-> execute($donnees); 
			return $exec->fetch(); //une seule classe trouvee 
		}
		/******** Gestion des professeurs ***************/
		public function insert_professeur($tab){
			$requete="insert into professeur values (null, :nom, :prenom,:email, :diplome);";
			$donnees =array(	":nom"=>$tab ['nom'],                  
								":prenom"=>$tab ['prenom'], 
								":email"=>$tab ['email'],   
								":diplome"=>$tab ['diplome'] ); 
			$exec= $this->unPdo->prepare ($requete);
			$exec-> execute($donnees); 
			}

		public function selectAll_professeurs(){
			$requete="select * from professeur ; ";
			$exec= $this->unPdo->prepare ($requete);
			$exec-> execute(); 
			return $exec->fetchAll(); //retourner toutes les professeurs
			}
		/********** Gestion des etudiants ***********/
		public function insert_etudiant($tab){
			$requete="insert into etudiant values (null, :nom, :prenom,:email, :tel, :adresse, :idclasse);";
			$donnees =array(	":nom"=>$tab ['nom'],        
								":prenom"=>$tab ['prenom'], 
								":email"=>$tab ['email'],   
								":tel"=>$tab ['tel'],
								":adresse"=>$tab ['adresse'],
								":idclasse"=>$tab ['idclasse'], 
								 ); 
			$exec= $this->unPdo->prepare ($requete);
			$exec-> execute($donnees); 
			}
		public function selectAll_etudiants(){
			$requete="select * from etudiant ; ";
			$exec= $this->unPdo->prepare ($requete);
			$exec-> execute(); 
			return $exec->fetchAll();  
			}	
		/********** Gestion des matieres ***************/

		/********* Gestion des users ******************/
		public function select_user($email, $mdp){
			$requete = "select * from user where email = :email and mdp = :mdp;"; 

			//tableau des données 
			$donnees = array(":email"=>$email, ":mdp"=>$mdp); 

			//preparation de la requete 
			$select = $this->unPdo->prepare ($requete); 
			//execution de la requete
			$select->execute ($donnees); //envoi des données 
			//Extraction du resultat 
			$unUser = $select->fetch(); //donne un seul resultat 
			return $unUser; 
		}

	}
	?>