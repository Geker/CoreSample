<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*,java.text.*"%>
<%@ page trimDirectiveWhitespaces="true" %>
<html>
<head>
<title>sales report download</title>
</head>
<body>
	<%   Date date = new Date();
	Calendar c = Calendar.getInstance();
	{
		c.setTime(date);
		c.add(Calendar.DATE, -1);
	}
	String dt = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	String dtnow = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	%>



	<div id="formsContent">
		<h2>文件下载</h2>
		<p></p>
		<form:form action="down" id="form" method="post"
			modelAttribute="formBean" cssClass="cleanform">
			<div class="header">
			</div>


			<fieldset>
				<legend>参数</legend>


				<form:label path="startDate">
		  			开始日期 (yyyy-mm-dd)
				</form:label>
				<form:input path="startDate"  value="<%=dt%>" />
				<form:label path="endDate">
		  			截止日期 (yyyy-mm-dd)
				</form:label>
				<form:input path="endDate" value="<%=dtnow%>" />
			</fieldset>
			<p>
				<button type="submit">Submit</button>
			</p>
		</form:form>
	</div>

</body>
</html>
