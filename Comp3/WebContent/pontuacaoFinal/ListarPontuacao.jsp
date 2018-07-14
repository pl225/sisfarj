<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar pontuação final</title>
<link rel="stylesheet" type="text/css" href="css/tabela.css">
</head>
<body>

	<%@ page import = "br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter" %>

	<%
		ResultSetAdapter rs = (ResultSetAdapter) request.getAttribute("dados");
	%>
	
	<h2>Pontuação final</h2>
	
	<table>
		<thead>
			<tr>
				<th>Sigla</th>
				<th>Nome</th>
				<th>Pontuação final</th>
			</tr>
		</thead>
		<tbody>
		
			<%
				while (rs.next()) {
			%>
			
			<tr>
				<td><%= rs.getString("sigla") %></td>
				<td><%= rs.getString("nome") %></td>
				<td><%= rs.getInt("pontuacao") %></td>
			</tr>
			
			
			<%
				}
			%>
		
		</tbody>
	</table>
	

</body>
</html>