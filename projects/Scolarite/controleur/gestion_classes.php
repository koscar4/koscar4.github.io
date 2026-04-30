<h2> Gestion des classes </h2>
<?php
	
	$laClasse = null; 
	if(isset($_GET['action']) && isset($_GET['idclasse']))
	{
		$idclasse = $_GET['idclasse']; 
		$action   = $_GET['action'] ;
		switch ($action){
			case "edit" : 
				$laClasse = $unControleur->selectWhere_classe($idclasse); 
				break;
			case "sup"  : 
				$unControleur->delete_classe($idclasse); 
				break; 
		}
	}
	
	require_once("vue/vue_insert_classe.php");

	//insertion de la classe 
	if(isset($_POST["Valider"])){
		
		$unControleur->insert_classe($_POST);
		echo "<br> Insertion réussie de la classe ";
			
	}
	//modification de la classe.
	if(isset($_POST["Modifier"])){
		
		$unControleur->update_classe($_POST);
		
		//recharger la page 
		header("Location: index.php?page=2");
	}


	
	if (isset($_POST['Filtrer'])){
		//afficher les classes qui répondent au filtre
		$filtre = $_POST['filtre'];
		$lesClasses = $unControleur->selectLike_classes($filtre) ; 
	}else {
		//afficher toutes les classes 
		$lesClasses = $unControleur->selectAll_classes() ; 
	}

	require_once("vue/vue_select_classes.php");

?>







