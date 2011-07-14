<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page session="false" %>


<tags:template>
<jsp:attribute name="breadcrumb"><a href="." >Home </a></jsp:attribute>

<jsp:body>

<table class="itemlist">

<c:forEach items="${myfeeds}" var="post">
	<tr>
        <td>${post.user.id}</td>
		<td>${post.body.text}</td>
	</tr>
</c:forEach>
</table>

</jsp:body>
</tags:template>
