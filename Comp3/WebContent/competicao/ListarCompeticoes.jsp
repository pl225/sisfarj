<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Alterar associação</title>
	<link rel="stylesheet" type="text/css" href="css/tabela.css">
</head>
<body>

	<%@ page import = "java.sql.ResultSet" %>

	<%
		ResultSet rs = (ResultSet) request.getAttribute("dados");
	%>

	<h2>Competições:</h2>
	
	<table>
		<thead>
			<tr>
				<th>Nome</th>
				<th>Data</th>
				<th>Ações</th>
			</tr>
		</thead>
		<tbody>
		
			<%
				while (rs.next()) {
			%>
			
			<tr>
				<td><%= rs.getString("nome") %></td>
				<td><%= rs.getString("dataCompeticao") %></td>
				<td><a href="ListarCompeticao?dataCompeticao=
				<%= rs.getString("dataCompeticao") %>&endereco=<%= rs.getString("endereco") %>">Selecionar</a></td>
			</tr>
			
			
			<%
				}
			%>
		
		</tbody>
	</table>

</body>
</html>