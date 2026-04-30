<h3> Liste des professeurs </h3>

<table border="1">
	<tr>
		<td> Id professeur </td>
		<td> Nom Prof </td>
		<td> Prénom Prof </td>
		<td> Email Prof </td>
		<td> Diplôme Obtenu </td>

	</tr>
	<?php
	if (isset($lesProfesseurs)){
		foreach($lesProfesseurs as $unProfesseur) {
			echo "<tr>";
			echo "<td>".$unProfesseur['idprofesseur']."</td>"; 
			echo "<td>".$unProfesseur['nom']."</td>"; 
			echo "<td>".$unProfesseur['prenom']."</td>"; 
			echo "<td>".$unProfesseur['email']."</td>"; 
			echo "<td>".$unProfesseur['diplome']."</td>"; 
			echo "</tr>"; 
		}
	}
	?>
</table>