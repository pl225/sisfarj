<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Incluir Locais de Competição</title>
	<link rel="stylesheet" type="text/css" href="css/formulario.css">
</head>
<body>

	<%
		if (request.getAttribute("erro") != null) {
	%>
	<p><%= request.getAttribute("erro") %></p>
	<%
		}
	%>
	
	<div id="formulario">
	
		<h2>Insira os dados do Local de Competição</h2>
	
		<form action="IncluirLocalCompeticao" method="post">
			
			<label for="nome">Nome do Local</label>
			<input type="text" name="nome">
			
			<br/><br/>
			
			<label for="endereco">Endereço</label>
			<input type="text" name="endereco">
			
			<br/><br/>
			
			<fieldset>
			<legend>Piscinas</legend>
			
			<label for="piscina25">25 Metros</label>
			<input type="checkbox" name="piscina25" value="T">
			
			<br>
			
			<label for="piscina25">50 Metros</label>
			<input type="checkbox" name="piscina50" value="T">
			
			</fieldset>
			
			<br/><br/>
			
			<input type="submit" value="Enviar">
			
		</form>
	
	</div>
	
</body>
</html>