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

    <title>Create an account</title>

    <link href="${contextPath}/resources/css/basic.css" rel="stylesheet">
</head>

<body>

<div class="wrapper">

    <form:form method="POST" modelAttribute="creditCardModel">
        <h2>Add a credit card</h2>
        <spring:bind path="number">
            <div {${status.error ? 'has-error' : ''}">
                <form:input type="text" path="number" placeholder="Number" autofocus="true"></form:input><br/>
                <form:errors path="number"></form:errors><br/>
            </div>
        </spring:bind>
        <spring:bind path="name">
            <div {${status.error ? 'has-error' : ''}">
                <form:input type="text" path="name" placeholder="Name"></form:input><br/>
                <form:errors path="name"></form:errors><br/>
            </div>
        </spring:bind>
        <spring:bind path="expiryDate">
            <div {${status.error ? 'has-error' : ''}">
                <form:input type="date" path="expiryDate" placeholder="ExpiryDate"></form:input><br/>
                <form:errors path="expiryDate"></form:errors><br/>
            </div>
        </spring:bind>
        <button type="submit">Submit</button>
    </form:form>

    <form:form method="GET" action="${contextPath}/search">
        <button type="submit">Cancel</button>
    </form:form>

</div>
</body>
</html>