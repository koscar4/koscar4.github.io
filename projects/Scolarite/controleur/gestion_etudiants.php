<h2> Gestion des étudiats </h2>

<?php

	$lesClasses = $unControleur->selectAll_classes (); 

	require_once("vue/vue_insert_etudiant.php");

	if (isset($_POST['Valider'])){
		$unControleur->insert_etudiant($_POST); 
		echo "<br> Insertion réussie de l'étudiant.";
	}

	$lesEtudiants = $unControleur->selectAll_etudiants (); 

	require_once("vue/vue_select_etudiants.php");
?>
