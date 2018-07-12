<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Transferir Atleta</title>
<link rel="stylesheet" type="text/css" href="css/tabela.css">
</head>
<body>
	
	<%@ page import = "java.sql.ResultSet" %>
	
	<%
		ResultSet rs = (ResultSet) request.getAttribute("dados");
	%>
	
	<h2>Atletas</h2>
	
	<table>
		<thead>
			<tr>
				<th>Matrícula</th>
				<th>Nome</th>
				<th>Ações</th>
			</tr>
		</thead>
		<tbody>
			
			<%
				while (rs.next()) {
			%>
			
			<tr>
				<td><%= rs.getString("matriculaAtleta") %></td>
				<td><%= rs.getString("nome") %></td>
				<td><a href="TransferirAtleta?matriculaAtleta=<%= rs.getString("matriculaAtleta") %>">Transferir</a></td>
			</tr>
			
			<%
				}
			%>
		
		</tbody>
	</table>
	
</body>
</html>