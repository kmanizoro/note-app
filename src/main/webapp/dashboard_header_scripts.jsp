	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ page isELIgnored="false"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
</head>
  
  <body>
    <div class="app-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">
	
	  <!-- Header -->
	
      <header class="app-header mdl-layout__header mdl-color--light-blue-800 mdl-color-text--white-400">
        <div class="mdl-layout__header-row">
          <span class="mdl-layout-title"> <spring:message code="ui.dashboard.common.noteapp" /> </span>
          <c:if test="${not empty dashboard && dashboard == 'dashboard'}">
          <div class="dashboard-cmn-actions">
			<div id="dash_brd_icon_add" class="dashboard-cmn-btn-icon">
			  <i class="material-icons">edit</i>
			</div>
			<span class="mdl-tooltip mdl-tooltip--right" for="dash_brd_icon_add"><spring:message code="ui.dashboard.common.action.new" /></span>
			
			<div id="dash_brd_icon_delete" onclick="deleteAllNoteSelDivs()" class="dashboard-cmn-btn-icon">
			  <i class="material-icons">delete</i>
			</div>
			<span class="mdl-tooltip mdl-tooltip--right" for="dash_brd_icon_delete"><spring:message code="ui.dashboard.common.action.delall" /></span>
			
			<div id="dash_brd_icon_check" onclick="checkAllNoteDivsFn()" class="dashboard-cmn-btn-icon">
			  <i class="material-icons" id="dash_brd_icon_check_icon">done_all</i>
			</div>
			<span class="mdl-tooltip mdl-tooltip--right" for="dash_brd_icon_check"><spring:message code="ui.dashboard.common.action.selall" /></span>
			
			<div id="dash_brd_icon_autosave" class="mdl-cell--hide-phone dashboard-cmn-btn-icon">
				<label class="mdl-switch mdl-js-switch mdl-js-ripple-effect" for="dash_brd_icon_switch">
				  <input type="checkbox" id="dash_brd_icon_switch" class="mdl-switch__input">
				</label>
			</div>
			<span class="mdl-tooltip mdl-tooltip--right" for="dash_brd_icon_autosave"><spring:message code="ui.dashboard.common.action.autosave" /></span>
			
			<div id="dash_brd_icon_save" onclick="saveAllNoteDiv()" class="dashboard-cmn-btn-icon">
			  <i class="material-icons">save</i>
			</div>
			<span class="mdl-tooltip mdl-tooltip--right" for="dash_brd_icon_save"><spring:message code="ui.dashboard.common.action.saveas" /></span>
          </div>
          </c:if>
          <div class="mdl-layout-spacer"></div>
          
          <div id="commonSearchLabel" class="mdl-cell--hide-tablet mdl-cell--hide-phone mdl-textfield mdl-js-textfield mdl-textfield--expandable">
            <label class="mdl-button mdl-js-button mdl-button--icon" for="commonSearch">
              <i class="material-icons">search</i>
            </label>
            <div class="mdl-textfield__expandable-holder">
              <input class="mdl-textfield__input" type="text" id="commonSearch">
              <label class="mdl-textfield__label" for="search">Search...</label>
            </div>
          </div>
          
        </div>
      </header>
	  
	  <!--- Left Menu -->
	  
	  <c:set var="sessionUserId" value="0" /><c:if test="${not empty sessionScope.ssnUserId}"><c:set var="sessionUserId" value="${sessionScope.ssnUserId}" /></c:if>
	  <c:set var="sessionUserName" value="TestUser" /><c:if test="${not empty sessionScope.ssnUserName}"><c:set var="sessionUserName" value="${sessionScope.ssnUserName}" /></c:if>
	  <c:set var="sessionUserDspName" value="${sessionUserName}" /><c:if test="${not empty sessionScope.ssnUserDispName}"><c:set var="sessionUserDspName" value="${sessionScope.ssnUserDispName}" /></c:if>
	  <c:set var="sessionLoginSsnId" value="0" /><c:if test="${not empty sessionScope.ssnLoginSsnId}"><c:set var="sessionLoginSsnId" value="${sessionScope.ssnLoginSsnId}" /></c:if>
	  <c:set var="sessionLoginId" value="0" /><c:if test="${not empty sessionScope.ssnLoginId}"><c:set var="sessionLoginId" value="${sessionScope.ssnLoginId}" /></c:if>
	  
	  <div class="display-hide">
			<input type="hidden" name="ssnUserId" id="ssnUserId" value='<c:out value="${sessionUserId}" />'>
			<input type="hidden" name="ssnUserName" id="ssnUserName" value='<c:out value="${sessionUserName}" />'>
			<input type="hidden" name="ssnUserDispName" id="ssnUserDispName" value='<c:out value="${sessionUserDspName}" />'>
			<input type="hidden" name="ssnLoginSsnId" id="ssnLoginSsnId" value='<c:out value="${sessionLoginSsnId}" />'>
			<input type="hidden" name="ssnLoginId" id="ssnLoginId" value='<c:out value="${sessionLoginId}" />'>
	  </div>
	  
      <div class="app-drawer mdl-layout__drawer mdl-color--blue-900">
        <header class="app-drawer-header">
          <i class="material-icons md-60">face</i>
          <div class="app-avatar-dropdown">
			<span id="userDispNameId" class="userDispNameClass"><c:out value="${sessionUserDspName}" /></span>
          </div>
        </header>
        <nav class="app-navigation mdl-navigation mdl-color--light-blue-800 mdl-color-text--white-400">
          <a class="mdl-navigation__link" href="dashboard"><i class="mdl-color-text--white-400 material-icons md-30" role="presentation">import_contacts</i><spring:message code="ui.dashboard.common.action.link.notes" /></a>
          <a class="mdl-navigation__link" href="profile"><i class="mdl-color-text--white-400 material-icons md-30" role="presentation">account_circle</i><spring:message code="ui.dashboard.common.action.link.profile" /></a>
          <a class="mdl-navigation__link" href="#"><i class="mdl-color-text--white-400 material-icons md-30" role="presentation">local_offer</i><spring:message code="ui.dashboard.common.action.link.tags" /></a>
          <a class="mdl-navigation__link" href="#"><i class="mdl-color-text--white-400 material-icons md-30" role="presentation">history</i><spring:message code="ui.dashboard.common.action.link.history" /></a>
          <a class="mdl-navigation__link" href="support"><i class="mdl-color-text--white-400 material-icons md-30" role="presentation">help_outline</i><spring:message code="ui.dashboard.common.action.link.support" /></a>
          <a class="mdl-navigation__link" href="logout"><i class="mdl-color-text--white-400 material-icons md-30" role="presentation">power_settings_new</i><spring:message code="ui.dashboard.common.action.link.logout" /></a>
        </nav>
      </div>