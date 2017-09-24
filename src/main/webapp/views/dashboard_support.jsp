<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%-- DashBoard Header --%>
<jsp:include page="dashboard_header.jsp" />
<jsp:include page="dashboard_header_scripts.jsp" />
<%-- DashBoard Content --%>

<main class="mdl-layout__content mdl-color--grey-100">

<div ng-app="support-app" ng-controller="support-ctrl" class="mdl-grid app-content">

	<div 
		ng-form name="supportFrm"
		class="mdl-cell mdl-cell--8-col-desktop mdl-cell--6-col-tablet mdl-cell--4-col-phone">
		<div class="mdl-card mdl-card mdl-color--grey-100">
			<div class="mdl-card__title">
				<h2 class="mdl-card__title-text mdl-color-text--blue-800"><spring:message code="ui.profile.account.cancel"/></h2>
			</div>
			<div class="mdl-card__supporting-text">
				<div
					class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					<input type="text" class="mdl-textfield__input" id="user"
						ng-model="user" name="user" placeholder="<spring:message code="ui.help.name"/>"
						ng-minlength="4"
						ng-maxlength="30"> 
					<label class="mdl-textfield__label" for="user"> <spring:message code="ui.help.name"/></label>
				</div>

				<div
					class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					<input type="email" ng-model="email" placeholder="<spring:message code="ui.profile.account.emailid"/>" 
						class="mdl-textfield__input" id="email" name='email' >
					<label class="mdl-textfield__label" for="email"><spring:message code="ui.profile.account.emailid"/> </label>
				</div>
				
				<div
					class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					<textarea type="text" ng-model="content"
						class="mdl-textfield__input" id="content" rows="5" name='content'
						ng-minlength="2"
						ng-maxlength="300"
						placeholder='<spring:message code="ui.help.txt"/>'
						> </textarea>
					<label class="mdl-textfield__label" for="content"><spring:message code="ui.help.txt"/> </label>
				</div>
				
				<div>
					<input type="hidden" id="csrf_tkn" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input type="hidden" id="csrf_hdr" value="${_csrf.headerName}" />
				</div>

			</div>

			<div class="mdl-card__actions">
				<button 
					class="mdl-button mdl-button--colored mdl-color--blue-600 mdl-color-text--white mdl-js-button mdl-js-ripple-effect"
					type="button" ng-click="sendSupportDetailsFun()" 
					ng-disabled="!isShowSubmitBtn || 
					(supportFrm.user.$invalid || !supportFrm.user.$dirty || supportFrm.user.$pristine) ||
					(supportFrm.email.$invalid || !supportFrm.email.$dirty || supportFrm.email.$pristine) ||
					(supportFrm.content.$invalid || !supportFrm.content.$dirty || supportFrm.content.$pristine)" 
					
					> <spring:message code="ui.help.submit"/></button>
			</div>
		</div>
	</div>
	
</div>

</main>
<%-- DashBoard Footer --%>
<jsp:include page="dashboard_footer_scripts.jsp" />
<script type="text/javascript" src="res/js/support.js"></script>
<jsp:include page="dashboard_footer.jsp" />