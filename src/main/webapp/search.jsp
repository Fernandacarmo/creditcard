<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Credit Card">
    <meta name="author" content="Fernanda">

    <title>Search a credit card</title>

    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet">
</head>
<body>
<div class="wrapper">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
    </c:if>

    <form:form method="POST" action="${contextPath}/search">
        <h2>Search for credit card</h2>
        <div {${error != null ? 'has-error' : ''}">
            <span>${message}</span><br/>
            <input name="number" type="text" placeholder="number" autofocus="true"/><br/><br/>
            <span>${error}</span><br/>
            <button type="submit">Search</button>
    </form:form>

    <c:if test="${pageContext.request.userPrincipal.name != null}">

        <table>
            <tr>Credit card list:</tr>
            <c:forEach items="" >
                <tr><td>item</td></tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>
