<h3> Ajout d'une classe </h3>

<form method ="post">
	<table border="0">
		<tr> 
			<td> Nom de la classe </td>
			<td> <input type="text" name="nom" 
				value="<?= ($laClasse==null)? '':$laClasse['nom'] ?>"> </td>
		</tr>
		<tr> 
			<td> Salle de cours  </td>
			<td> <input type="text" name="salle" 
				value="<?= ($laClasse==null)? '':$laClasse['salle'] ?>"> </td>
		</tr>
		<tr> 
			<td> Diplôme préparé </td>
			<td> <input type="text" name="diplome" 
				value="<?= ($laClasse==null)? '':$laClasse['diplome'] ?>"> </td>
		</tr>
		<tr> 
			<td> <input type="reset" name ="Annuler" value="Annuler"> </td>
			<td> <input type="submit" 
		<?= ($laClasse==null) ? ' name="Valider" value="Valider" ' :  
								' name="Modifier" value="Modifier" ' ?>
				> </td>
		</tr>
	</table>
	<?= ($laClasse==null) ? '' : '<input type="hidden" name="idclasse" value="'.$_GET['idclasse'].'">' ?> 
</form>













 