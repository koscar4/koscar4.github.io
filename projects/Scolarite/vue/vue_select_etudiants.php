<h3> Liste des étudiants </h3>
<table border="1">
	<tr>
		<td> Id Etudiant </td>
		<td> Nom </td>
		<td> Prénom </td>
		<td> Email </td>
		<td> Téléphone </td>
		<td> Adresse </td>
		<td> Classe  </td>
	</tr>
	<?php
	if (isset($lesEtudiants)){
		foreach ($lesEtudiants as $unEtudiant) {
			echo "<tr>"; 
			echo "<td>".$unEtudiant['idetudiant']."</td>"; 
			echo "<td>".$unEtudiant['nom']."</td>"; 
			echo "<td>".$unEtudiant['prenom']."</td>"; 
			echo "<td>".$unEtudiant['email']."</td>"; 
			echo "<td>".$unEtudiant['tel']."</td>"; 
			echo "<td>".$unEtudiant['adresse']."</td>";
			echo "<td>".$unEtudiant['idclasse']."</td>";  
			echo "</tr>";
		}
	}
	?>
</table>