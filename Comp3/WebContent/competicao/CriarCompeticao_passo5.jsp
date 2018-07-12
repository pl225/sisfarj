<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Criar Competição</title>
</head>
<body>

	<%@ page import = "br.sisfarj.ccomp.dominio.ProvaMT.NomeProva" %>
	<%@ page import = "br.sisfarj.ccomp.dominio.ProvaMT.Classe" %>
	<%@ page import = "br.sisfarj.ccomp.dominio.ProvaMT.Categoria" %>

	<%
		 NomeProva nomesProva [] = NomeProva.values();
		 Categoria categorias [] = Categoria.values();
		 Classe classes [] = Classe.values();
	%>

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
	%>
	
	<div id="formulario">
	
		<h2>Insira os Dados da Competição</h2>
	
		<form action="CriarCompeticao?passo5=true" method="post">
			
			<label for="LocaisDeProva">Local de prova / Tipo da piscina</label>
			<select name="LocaisDeProva">
			
				<option value="">(Selecione uma opção)</option>
				
				<%
						while (rs.next()) {
				%>
				
				<optgroup label="<%= rs.getString("nome") %>">
					
					<%
						if (rs.getString("piscina25").toCharArray()[0] == 'T') {
					%>
					<option value="<%= "" + rs.getString("endereco") + 
					"&25M" %> ">25M</option>
					<%
						}
					%>
					
					<%
						if (rs.getString("piscina50").toCharArray()[0] == 'T') {
					%>
					<option value="<%= "" + rs.getString("endereco") + 
					"&50M" %> ">50M</option>
					<%
						}
					%>
				
				</optgroup>
				<%
					}
				%>
				
				
			</select>
			
			<br/><br/>
			
			<label for="nomesProva">Nomes de prova</label>
			<select name="nomesProva" multiple>
				<%
					for (NomeProva np : nomesProva) {
				%>
				
				<option value="<%= np.toString() %>" ><%= np.toString() %></option>
				
				<%
					}
				%>
			</select>
			
			<br/><br/>
			
			<label for="categorias">Categorias</label>
			<select name="categorias" multiple>
				<%
					for (Categoria np : categorias) {
				%>
				
				<option value="<%= np.toString() %>" ><%= np.toString() %></option>
				
				<%
					}
				%>
			</select>
			
			<br/><br/>
			
			<label for="classes">Classes</label>
			<select name="classes" multiple>
				<%
					for (Classe np : classes) {
				%>
				
				<option value="<%= np.toString() %>" ><%= np.toString() %></option>
				
				<%
					}
				%>
			</select>
			
			<br/><br/>
			
			<input type="submit" value="Enviar">
			
		</form>
	
	</div>
	
</body>
</html>