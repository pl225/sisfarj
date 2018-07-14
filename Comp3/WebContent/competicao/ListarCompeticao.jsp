<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/tabela.css">
<title>Listar Competi��o</title>
</head>
<body>
		<%@ page import = "br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter" %>

	<%
		ResultSetAdapter rsProvas = (ResultSetAdapter) request.getAttribute("dados");
		
		ResultSetAdapter rsCompeticao = (ResultSetAdapter) request.getAttribute("dadosCompeticao");
		
		
	%>

	<h2>Provas da Competi��o <%= rsCompeticao.getString("nome") %></h2>
		<br/><br/>
		
		Tipo de Piscina: <%= rsCompeticao.getString("tipoPiscina") %>
		
		<table>
		<thead>
			<tr>
				<th>Nome</th>
				<th>Categoria</th>
				<th>Classe</th>
				<th>A��es</th>
			</tr>
		</thead>
		<tbody>
		
			<%
				while (rsProvas.next()) {
			%>
			
			<tr>
				<td><%= rsProvas.getString("nomeProva") %></td>
				<td><%= rsProvas.getString("categoria") %></td>
				<td><%= rsProvas.getString("classe") %></td>
				<td><a href="ListarCompeticao?nome=<%= rsProvas.getString("nomeProva") %>
				&endereco=<%=rsCompeticao.getString("endereco")%>&classe=<%=rsProvas.getString("classe")%>&categoria=<%=rsProvas.getString("categoria")%>&dataCompeticao=<%=rsCompeticao.getString("dataCompeticao")%>">Selecionar</a></td>
			</tr>
			
			
			<%
				}
			%>
		
		</tbody>
	</table>
		
		<br/><br/>
		
		
	</form>
</body>
</html>