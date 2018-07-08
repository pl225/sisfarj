<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Identificar usuário</title>
</head>
<body>

<h2>Identifique-se</h2>

	<%
		if (request.getAttribute("erro") != null) {
	%>
	<p><%= request.getAttribute("erro") %></p>
	<%
		}
	%>

<form method="post" action="IdentificarUsuario">
	<label for="matricula">Matrícula</label>
	<input type="text" name="matricula">
	
	<label for="senha">Senha</label>
	<input type="senha" name="senha">
	
	<input type="submit" value="Login">
</form>

</body>
</html>