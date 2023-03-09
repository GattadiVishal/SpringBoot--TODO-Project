
<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">

	<h1>Enter Todo Details</h1>
	
	<form:form method="post" modelAttribute="todo">
		<!-- here we are grouping all the related elements in fieldset -->
		<fieldset class="mb-3">
			<!-- here we are adding margin/gap b/w the text area and submit button -->
			<!-- mb = margin botton 3 spaces -->
			<form:label path="description">Description</form:label>
			<form:input type="text" path="description" required="required" />
			<form:errors path="description" cssClass="text-warning" />
		</fieldset>

		<fieldset class="mb-3">
			<form:label path="targetDate">Target Date</form:label>
			<form:input type="text" path="targetDate" required="required" />
			<form:errors path="targetDate" cssClass="text-warning" />
		</fieldset>

		<!-- Here we are creating hidden variables for id, done -->
		<form:input type="hidden" path="id" />
		<form:input type="hidden" path="done" />
		<input type="submit" class="btn btn-success">
	</form:form>
	
</div>
<%@ include file="common/footer.jspf"%>

<script type="text/javascript">
		$('#targetDate').datepicker({
			format : 'yyyy-mm-dd' //in datepicker framework month is small m.
		});
	</script>
<!-- modelAttribute binds the description in the todo bean -->