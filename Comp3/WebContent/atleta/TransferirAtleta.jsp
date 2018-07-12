<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tranferir Atleta</title>
<link rel="stylesheet" type="text/css" href="css/formulario.css">
</head>
<body>

	<h2>Transferir Atleta</h2><br></br>
	
		<%
			if (request.getAttribute("erro") != null) {
		%>
		<p><%= request.getAttribute("erro") %></p>
		<%
			}
		%>
		
		<%@ page import = "java.sql.ResultSet" %>

		<%
			ResultSet rs = (ResultSet) request.getAttribute("dados");
			rs.next();
		%>
		
	<form method="post" action="TransferirAtleta">
		
		<fieldset>
		<legend>Transferir Atleta <%= rs.getString("nome") %> - <%= rs.getString("matriculaAtleta") %></legend>
		<input type="hidden" name="matriculaAtleta" value="<%= rs.getString("matriculaAtleta") %>">
		<br></br>
		
		<label for="associacao">Matrícula da Associação</label>
		<input type="text" name="associacao">
		<br></br>
		
		<label for="numero">Número do Ofício</label>
		<input type="text" name="numero">
		<br></br>
		
		<label for="oficio">Data do Ofício</label>
		<input type="date" name="oficio">
		<br></br>
		
		<label for="comprovante">Número do Comprovante</label>
		<input type="text" name="comprovante">	
		<br></br>
		
		<label for="entrada">Data de Entrada na Associação</label>
		<input type="date" name="entrada">
		<br></br>
					
		<input type="submit" value="Transferir">
		</fieldset>
	</form>

</body>
</html>