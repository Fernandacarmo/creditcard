<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="description" content="Credit Card">
    <meta name="author" content="Fernanda">

    <title>Create an account</title>

    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet">
</head>

<body>

<div class="wrapper">

    <form:form method="POST" modelAttribute="userModel">
        <h2 >Create your account</h2>
        <spring:bind path="username">
            <div {${status.error ? 'has-error' : ''}">
                <form:input type="text" path="username" placeholder="Username" autofocus="true"></form:input><br/>
                <form:errors path="username"></form:errors><br/>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div {${status.error ? 'has-error' : ''}">
                <form:input type="password" path="password" placeholder="Password"></form:input><br/>
                <form:errors path="password"></form:errors><br/><br/>
            </div>
        </spring:bind>
        <button type="submit">Submit</button><br/><br/>
    </form:form>

    <form:form method="GET" action="${contextPath}/logout">
        <button type="submit">Cancel</button>
    </form:form>

</div>
</body>
</html>
