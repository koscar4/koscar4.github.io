<h3> Ajout d'un étudiant </h3>

<form method ="post">
	<table border="0">
		<tr> 
			<td> Nom Etudiant </td>
			<td> <input type="text" name="nom"> </td>
		</tr>
		<tr> 
			<td> Prénom Etudiant  </td>
			<td> <input type="text" name="prenom"> </td>
		</tr>
		<tr> 
			<td> Email contact </td>
			<td> <input type="text" name="email"> </td>
		</tr>
		<tr> 
			<td> Tél contact </td>
			<td> <input type="text" name="tel"> </td>
		</tr>
		<tr> 
			<td> Adresse postale </td>
			<td> <input type="text" name="adresse"> </td>
		</tr>
		<tr> 
			<td> La classe </td>
			<td> <select name="idclasse">
				<?php
				foreach ($lesClasses as $uneClasse){
				echo "<option value='".$uneClasse['idclasse']."'>"; 
				echo $uneClasse['idclasse']."-".$uneClasse['nom']; 
				echo "</option>"; 
				}
				?>	
				</select>	
			 </td>
		</tr>
		<tr> 
			<td> <input type="reset" name ="Annuler" value="Annuler"> </td>
			<td> <input type="submit" name="Valider" value="Valider"> </td>
		</tr>
	</table>
</form> 