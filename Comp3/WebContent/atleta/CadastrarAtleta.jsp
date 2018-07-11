<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar atleta</title>
</head>
<body>

	<h2>Insira as informações do Atleta</h2>
	
		<%
			if (request.getAttribute("erro") != null) {
		%>
		<p><%= request.getAttribute("erro") %></p>
		<%
			}
		%>
		
	<form method="post" action="CadastrarAtleta">
		<label for="numero">Número</label>
		<input type="text" name="numero">
		<br></br>
		<label for="oficio">Data do Ofício</label>
		<input type="date" name="oficio">
		<br></br>
		<label for="nome">Nome</label>
		<input type="text" name="nome">
		<br></br>
		<label for="nascimento">Data de Nascimento</label>
		<input type="date" name="nascimento">
		<br></br>
		<label for="entrada">Data de Entrada na Associação</label>
		<input type="date" name="entrada">
		<br></br>
		<label for="associacao">Matrícula da Associação</label>
		<input type="text" name="associacao">
		<br></br>
		<label for="comprovante">Número do Comprovante</label>
		<input type="text" name="comprovante">	
		<br></br>	
		<input type="submit" value="Cadastrar">
	</form>

</body>
</html>