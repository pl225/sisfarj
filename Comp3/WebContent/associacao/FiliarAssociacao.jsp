<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Filiar associação</title>
</head>
<body>

	<h2>Insira os dados da Associação</h2>
	
	<jsp:include page="/WEB-INF/Erro.jsp"></jsp:include>

	<form action="FiliarAssociacao" method="post">
		
		<label for="numeroOficio">Nº do ofício</label>
		<input type="text" name="numeroOficio">
		
		<br/><br/>
		
		<label for="dataOficio">Data do ofício</label>
		<input type="date" name="dataOficio">
		
		<br/><br/>
		
		<label for="nome">Nome</label>
		<input type="text" name="nome">
		
		<br/><br/>
		
		<label for="sigla">Sigla</label>
		<input type="text" name="sigla">
		
		<br/><br/>
		
		<label for="endereco">Endereço</label>
		<input type="text" name="endereco">
		
		<br/><br/>
		
		<label for="telefone">Telefone</label>
		<input type="text" name="telefone">
		
		<br/><br/>
		
		<label for="numeroComprovantePagamento">Nº do comprovante de pagamento</label>
		<input type="text" name="numeroComprovantePagamento">
		
		<br/><br/>
		
		<input type="submit" value="Enviar">
		
	</form>

</body>
</html>