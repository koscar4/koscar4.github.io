<h3> Liste des classes </h3>

<form method="post">
	Filter par : <input type="text" name="filtre">
	<input type="submit" name="Filtrer" value="Filtrer">
</form>
<br>

<table border="1">
	<tr>
		<td> Id classe </td>
		<td> Nom Classe </td>
		<td> Salle de cours </td>
		<td> Diplôme Préparé </td>

		<td> Opérations </td>

	</tr>
	<?php
	if (isset($lesClasses)){
		foreach($lesClasses as $uneClasse) {
			echo "<tr>";
			echo "<td>".$uneClasse['idclasse']."</td>"; 
			echo "<td>".$uneClasse['nom']."</td>"; 
			echo "<td>".$uneClasse['salle']."</td>"; 
			echo "<td>".$uneClasse['diplome']."</td>"; 
			echo "<td>";
			echo "<a href='index.php?page=2&action=edit&idclasse=".$uneClasse['idclasse']."'><img src='images/modifier.png' width='30' height='30'> </a>";


			echo "<a href='index.php?page=2&action=sup&idclasse=".$uneClasse['idclasse']."'><img src='images/supprimer.png' width='30' height='30'> </a>";


			echo "</td>";
			echo "</tr>"; 
		}
	}
	?>
</table>









