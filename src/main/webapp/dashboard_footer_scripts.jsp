	<%@ page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	<div id="msgSnackBar" class="mdl-js-snackbar mdl-snackbar">
		<div class="mdl-snackbar__text"></div>
		<button id="msgSnackBarBtn" class="mdl-snackbar__action" type="button"></button>
	</div>
	<c:if test="${not empty MSG_ERR && MSG_ERR != ''}">
		<input type="hidden" id="MSG_ERR" value='<c:out value="${MSG_ERR}"/>'>
	</c:if>
	<c:if test="${not empty Success && Success != ''}">
		<input type="hidden" id="SuccessId" value='<c:out value="${Success}"/>'>
	</c:if>
	</div>
	
	<script type="text/javascript" src="res/js/funs.js"></script>
    <script type="text/javascript" src="res/js/material.min.js"></script>
	<script type="text/javascript" src="res/js/angular.min.js"></script>
	<script type="text/javascript" src="res/js/angular-sanitize.min.js"></script>
 