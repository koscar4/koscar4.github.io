<?php
	session_start(); 
	require_once("controleur/controleur.class.php"); 
	$unControleur = new Controleur();
?>

<html>
	<head>
		<title> Scolarite </title>
		<meta charset="utf-8">
	</head>
	
<body>
	<center>
		<h1> Site de scolarité IRIS </h1>

		<?php
			if( ! isset($_SESSION['email'])){
				require_once("vue/vue_connexion.php");
			}

			if(isset($_POST['Connexion']))
			{
				$email = $_POST['email']; 
				$mdp   = $_POST['mdp']; 
				$unUser = $unControleur->select_user($email,$mdp); 
				if ($unUser == null){
					echo "<br> Veuillez vérifier vos identifiants";
				}
				else {
					//on enregistre les données dans Session 
					$_SESSION['email'] = $unUser['email']; 
					$_SESSION['nom'] = $unUser['nom']; 
					$_SESSION['prenom'] = $unUser['prenom']; 
					//on recharge la page 
					header("Location: index.php?page=1");
				}
			}
		if(isset($_SESSION['email'])) {
		echo '
		<a href="index.php?page=1"> <img src="images/logo.png" width="100" height="100"> </a>
		<a href="index.php?page=2"> <img src="images/classes.png" width="100" height="100"> </a>
		<a href="index.php?page=3"> <img src="images/etudiants.png" width="100" height="100"> </a>
		<a href="index.php?page=4"> <img src="images/profs.png" width="100" height="100"> </a>
		<a href="index.php?page=5"> <img src="images/matieres.png" width="100" height="100"> </a>
		<a href="index.php?page=6"> <img src="images/deconnexion.png" width="100" height="100"> </a>
		';
		
		if(isset($_GET["page"])) $page = $_GET["page"] ;
			else $page = 1; 
			
			switch($page){
			case 1 : require_once ("controleur/home.php"); break; 
			case 2 : require_once ("controleur/gestion_classes.php"); break;
			case 3 : require_once ("controleur/gestion_etudiants.php"); break;
			case 4 : require_once ("controleur/gestion_profs.php"); break;
			case 5 : require_once ("controleur/gestion_matieres.php"); break;
			
			case 6 :
				session_destroy();
				unset($_SESSION['email']);
				header("Location: index.php"); 
				break;
				
			default : require_once ("controleur/erreur.php"); break;
			}
		}
		?>
	</center>
</body>
</html>

