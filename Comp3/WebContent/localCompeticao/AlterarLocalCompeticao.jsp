<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Listar Locais de Competição</title>
	<link rel="stylesheet" type="text/css" href="css/tabela.css">
</head>
<body>

	<%@ page import = "java.sql.ResultSet" %>

	<%
		ResultSet rs = (ResultSet) request.getAttribute("dados");
	%>

	<h2>Selecione um local de competição</h2>
	
	<table>
		<thead>
			<tr>
				<th>Nome</th>
				<th>Endereço</th>
				<th>Piscina 25m</th>
				<th>Piscina 50m</th>
				<th>Ações</th>
			</tr>
		</thead>
		<tbody>
		
			<%
				while (rs.next()) {
			%>
			
			<tr>
				<td><%= rs.getString("nome") %></td>
				<td><%= rs.getString("endereco") %></td>
				<td><%= rs.getString("piscina25") %></td>
				<td><%= rs.getString("piscina50") %></td>
				<td><a href="AlterarLocalCompeticao?endereco=
				<%= rs.getString("endereco") %>">Alterar</a></td>
			</tr>
			
			
			<%
				}
			%>
		
		</tbody>
	</table>

</body>
</html>