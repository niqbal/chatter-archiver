<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page session="false" %>

<tags:template>

	<jsp:attribute name="breadcrumb"><a href="." >Home </a></jsp:attribute>

<jsp:body>
    <a href="getfeeds" >Pull feeds from SFDC</a>
    <br/>
    <a href="getarchived" >View stored feeds (mongo)</a>

</jsp:body>
</tags:template>
