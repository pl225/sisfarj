<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SISFARJ</title>
</head>
<body>

	<%@ page import = "br.sisfarj.ccomp.dominio.adapter.ResultSetAdapter" %>

	<%
		ResultSetAdapter rs = (ResultSetAdapter) request.getAttribute("dados");
		ResultSetAdapter locaisCompeticaoRs = (ResultSetAdapter) request.getAttribute("locaisCompeticao");
	%>

	<h2>Edite as informações da Competição <%= rs.getString("nome") %></h2>
	
	<form action="AlterarCompeticao?dataCompeticaoAtual=<%= rs.getString("dataCompeticao") %>
				&endereco=<%=rs.getString("endereco")%>" method="post">
		
		<label for="dataCompeticao">Data da competição</label>
		<input type="date" name="dataCompeticao" value="<%= rs.getTimestamp("dataCompeticao").toString().split(" ")[0] %>">
		
		<br/><br/>
		
		<label for="localCompeticao">Local da competição</label>
		<select name="localCompeticao">
			<option value="">(Selecione um local de competição)</option>
			
			<%
				while (locaisCompeticaoRs.next()) {
			%>
			
			<option <%= locaisCompeticaoRs.getString("endereco").equals(rs.getString("endereco")) ? "selected" : "" %> 
				value="<%= locaisCompeticaoRs.getString("endereco") %>">
				<%= locaisCompeticaoRs.getString("nome") %>
			</option>
			
			<%
				}
			%>
		</select>
		
		<br/><br/>
		
		<input type="submit" value="Enviar">
		
	</form>

</body>
</html>