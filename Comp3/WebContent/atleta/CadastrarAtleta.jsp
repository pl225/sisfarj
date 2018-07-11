<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastrar atleta</title>
</head>
<body>

	<h2>Insira as informa��es do Atleta</h2>
	
		<%
			if (request.getAttribute("erro") != null) {
		%>
		<p><%= request.getAttribute("erro") %></p>
		<%
			}
		%>
		
	<form method="post" action="CadastrarAtleta">
		<label for="numero">N�mero</label>
		<input type="text" name="numero">
		<br></br>
		<label for="oficio">Data do Of�cio</label>
		<input type="date" name="oficio">
		<br></br>
		<label for="nome">Nome</label>
		<input type="text" name="nome">
		<br></br>
		<label for="nascimento">Data de Nascimento</label>
		<input type="date" name="nascimento">
		<br></br>
		<label for="entrada">Data de Entrada na Associa��o</label>
		<input type="date" name="entrada">
		<br></br>
		<label for="associacao">Matr�cula da Associa��o</label>
		<input type="text" name="associacao">
		<br></br>
		<label for="comprovante">N�mero do Comprovante</label>
		<input type="text" name="comprovante">	
		<br></br>	
		<input type="submit" value="Cadastrar">
	</form>

</body>
</html>