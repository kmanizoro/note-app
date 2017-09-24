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

<div ng-app="profile-app" ng-controller="psrnl-ctrl" class="mdl-grid app-content">

	<div class="mdl-cell mdl-cell--6-col-desktop mdl-cell--4-col-tablet mdl-cell--4-col-phone">
		<div class="mdl-card mdl-card mdl-color--grey-100">
			<div class="mdl-card__title">
				<h2 class="mdl-card__title-text mdl-color-text--blue-800"><spring:message code="ui.profile.account.details"/></h2>
			</div>
			<div class="mdl-card__supporting-text">
				<div
					class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					<input type="text" class="mdl-textfield__input" id="username"
						ng-model="user.userName" > <label
						class="mdl-textfield__label" for="username"> <spring:message code="ui.profile.account.username"/>
						</label>
				</div>

				<div
					class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					<input type="{{inputType}}" class="mdl-textfield__input"
						id="password" ng-model="user.userPass"
						readonly="readonly"> <label
						class="pointer-events-auto mdl-textfield__label" for="password">
						<spring:message code="ui.profile.account.password"/>
						<div style="cursor:pointer; float: right; z-index: 1000;">
							<i ng-click="showHidePassFld()" title="Show or Hide"
								class="material-icons">{{showHideVisbilityIcon}}</i>&nbsp; 
							<i ng-click="showHideUpdatePassBlck()" title="Edit or Update"
								class="material-icons">{{editCloseIcon}}</i>
						</div>
					</label>
				</div>

				<div ng-show="isShowUpdatePassFld"
					class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					<input type="{{inputType2}}" pattern=".{4,15}"
						ng-model="newpassword" class="mdl-textfield__input"
						id="newpassword" ng-model="user.usernewpass"> <label
						class="pointer-events-auto mdl-textfield__label" for="newpassword"><spring:message code="ui.profile.account.new.password"/>
						<div style="float: right; z-index: 1000;">
							<i ng-click="showHidePassFld2()" class="material-icons">{{showHideVisbilityIcon2}}</i>
						</div>
					</label>
				</div>

				<div
					class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					<input type="email" ng-model="user.userMail"
						class="mdl-textfield__input" id="emailid" name='emailid'>
					<label class="mdl-textfield__label" for="emailid"><spring:message code="ui.profile.account.emailid"/> 
					</label>
				</div>
				<div>
					<input type="hidden" id="userId" value="{{user.userId}}" />
					<input type="hidden" id="csrf_tkn" name="${_csrf.parameterName}" value="${_csrf.token}" />
					<input type="hidden" id="csrf_hdr" value="${_csrf.headerName}" />
				</div>
				</br></br></br>
				
				<div class="mdl-textfield">
					<input type="button" value='<spring:message code="ui.profile.account.delaccount"/>'
						class="mdl-button mdl-button--colored mdl-color--red-500 mdl-color-text--white mdl-js-button mdl-js-ripple-effect" 
						id="delete_account" >
				</div>

			</div>

			<div class="mdl-card__actions">
				<button type="button" ng-click="updatePersonalDetails()" ng-disabled="!isShowUpdateBtn"
					class="mdl-button mdl-button--colored mdl-color--blue-600 mdl-color-text--white mdl-js-button mdl-js-ripple-effect">
					<spring:message code="ui.profile.account.update.details"/></button>
			</div>
		</div>
	</div>
	
	<div class="mdl-cell mdl-cell--6-col-desktop mdl-cell--4-col-tablet mdl-cell--4-col-phone">
		<div class="mdl-card mdl-card mdl-color--grey-100">
			<div class="mdl-card__title">
				<h2 class="mdl-card__title-text mdl-color-text--cyan-900"><spring:message code="ui.profile.account.personal.details"/></h2>
			</div>
			<div class="mdl-card__supporting-text">
				<div
					class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					<input type="text" class="mdl-textfield__input captailize" id="dispname"
						ng-model="user.userDisplayname"> <label
						class="mdl-textfield__label" for="dispname"> <spring:message code="ui.profile.account.disp.name"/> </label>
				</div>
				<div
					class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					<input type="text" class="mdl-textfield__input captailize" id="firstname"
						ng-model="user.userFirstname"> <label
						class="mdl-textfield__label" for="firstname"> <spring:message code="ui.profile.account.first.name"/> </label>
				</div>
				<div
					class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
					<input type="text" class="mdl-textfield__input captailize" id="lastname"
						ng-model="user.userLastname"> <label
						class="mdl-textfield__label" for="lastname"><spring:message code="ui.profile.account.last.name"/> </label>
				</div>
				
				<div style="padding-top: 25px;" class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label is-dirty is-upgraded">
				<label class="mdl-textfield__label" for="gender"> <spring:message code="ui.profile.account.gender"/></label>
					<label class="mdl-radio mdl-js-radio mdl-js-ripple-effect"
						for="gender-male"> <input type="radio" id="gender-male"
						class="mdl-radio__button" ng-model="user.userGender" name="gender" ng-select="user.userGender==='male'" value="male" >
						<span class="mdl-radio__label"><spring:message code="ui.profile.account.male"/></span>
					</label> &nbsp;&nbsp;
					<label class="mdl-radio mdl-js-radio mdl-js-ripple-effect"
						for="gender-female"> <input type="radio" id="gender-female"
						class="mdl-radio__button" ng-model="user.userGender" name="gender" ng-select="user.userGender==='female'" value="female" >
						<span class="mdl-radio__label"><spring:message code="ui.profile.account.female"/></span>
					</label> &nbsp;&nbsp;
					<label class="mdl-radio mdl-js-radio mdl-js-ripple-effect"
						for="gender-others"> <input type="radio" id="gender-others"
						class="mdl-radio__button" ng-model="user.userGender" name="gender" ng-select="user.userGender==='others'" value="others" >
						<span class="mdl-radio__label"><spring:message code="ui.profile.account.others"/></span>
					</label> 
				</div>
				
				<div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label" >
					<input type="date" class="mdl-textfield__input"
						id="userdob" ng-model="user.userDob" value="{{user.userDob}}"
						min="1950-01-26" max="2017-01-01" 
						> <label
						class="mdl-textfield__label" for="userdob"><spring:message code="ui.profile.account.dob"/> </label>
				</div>
				
			</div>
			
			
			<div class="mdl-card__actions">
				<button type="button" ng-click="savePersonalDetails()" ng-disabled="!isShowSaveBtn"
					class="mdl-button mdl-button--colored mdl-color--cyan-700 mdl-color-text--white mdl-js-button mdl-js-ripple-effect">
					<spring:message code="ui.profile.account.save.details"/>
				</button>
			</div>
		</div>
	</div>

	<dialog class="mdl-dialog" ng-form name="delCnfFrm">
	<div class="">
		<h5 style="font-size: 23px;" class="mdl-dialog__title mdl-color-text--blue-500"><spring:message code="ui.profile.account.are.sure"/></h5>
		<div class="mdl-dialog__content">
			<div
				class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
				<input type="password" pattern=".{4,15}" ng-model="delcnfpassword"
					class="mdl-textfield__input" id="delcnfpassword" value="password"
					ng-minlength="4" ng-maxlength="15" name='delcnfpassword'
					required="required"> <label
					class="pointer-events-auto mdl-textfield__label"
					for="delcnfpassword"><spring:message code="ui.profile.account.cnf.password"/></label>
			</div>
		</div>
		<div class="mdl-dialog__actions">
			<button type="button" id="acc-del-confirm"
				ng-disabled="delCnfFrm.delcnfpassword.$invalid || !delCnfFrm.delcnfpassword.$dirty || delCnfFrm.delcnfpassword.$pristine"
				class="mdl-button mdl-button--colored mdl-color--blue-500 mdl-color-text--white"><spring:message code="ui.dashboard.common.div.ok"/></button>
			<button type="button" class="mdl-button close"><spring:message code="ui.profile.account.cancel"/></button>
		</div>
	</div>
	</dialog>

</div>

</main>
<%-- DashBoard Footer --%>
<jsp:include page="dashboard_footer_scripts.jsp" />
<script type="text/javascript" src="res/js/profile.js"></script>
<jsp:include page="dashboard_footer.jsp" />