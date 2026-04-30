<?php
	require_once ("modele/modele.class.php");
	
	class Controleur {
		private $unModele ; 
			
		public function __construct(){
			$this->unModele = new Modele ();
		}
		public function insert_classe ($tab){
			//partie sécurité des donnees
			
			//appel du modele pour l'insertion
			$this->unModele->insert_classe($tab);
		}

		public function selectAll_classes (){
			//on récupere les classes 
			$lesClasses = $this->unModele->selectAll_classes (); 
			//je realise des controles 

			//retourner a la vue les classses 
			return $lesClasses;
		}

		public function selectLike_classes ($filtre){
			$lesClasses = $this->unModele->selectLike_classes ($filtre); 
			return $lesClasses;
		}

		public function delete_classe ($idclasse){
			//des controles  faire 
			$this->unModele->delete_classe($idclasse);
		}

		public function update_classe ($tab){
			//des controles de données 

			$this->unModele->update_classe($tab);
		}

		public function selectWhere_classe ($idclasse){

			$uneClasse = $this->unModele->selectWhere_classe ($idclasse);
			return $uneClasse; 
		}
		/************* Gestion des profs s*************/
		public function insert_Professeur ($tab){
			//partie sécurité des donnees
			
			//appel du modele pour l'insertion
			$this->unModele->insert_Professeur($tab);
		}

		public function selectAll_professeurs (){
			//on récupere les Professeurs 
			$lesProfesseurs = $this->unModele->selectAll_professeurs (); 
			//je realise des controles 

			//retourner a la vue les Professeurs 
			return $lesProfesseurs;
		}
		/************* Gestion des etudiants *************/
		public function insert_etudiant ($tab){
			$this->unModele->insert_etudiant($tab);
		}

		public function selectAll_etudiants (){
			$lesEtudiants = $this->unModele->selectAll_etudiants (); 
			return $lesEtudiants;
		}

		/********** Gestion des users **********/
		public function select_user ($email, $mdp){
			$unUser = $this->unModele->select_user ($email, $mdp); 
			return $unUser; 
		}
	}
?>





