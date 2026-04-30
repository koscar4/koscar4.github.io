<h2> Gestion des professeurs </h2>

<?php
	require_once("vue/vue_insert_professeur.php");

	//insertion du prof  
	if(isset($_POST["Valider"])){
		
		$unControleur->insert_Professeur($_POST);
		echo "<br> Insertion réussie du professeur ";
			
	}

	//afficher toutes les professeurs 
	$lesProfesseurs = $unControleur->selectAll_professeurs() ; 
	require_once("vue/vue_select_professeurs.php");
?>