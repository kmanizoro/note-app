<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<%-- DashBoard Header --%>
<jsp:include page="dashboard_header.jsp" />
<link rel="stylesheet" href="res/css/datepicker.min.css">
<link rel="stylesheet" href="res/css/material-datetime-picker.css">
<jsp:include page="dashboard_header_scripts.jsp" />
<%-- DashBoard Content --%>


<%-- DashBoard Content --%>

<main class="mdl-layout__content mdl-color--grey-300">

<div ng-app="dashboard-app" ng-controller="dash-main-ctrl" id="dash-main-app-id"  class="mdl-grid app-content">

<%-- Main Note Dialog Start --%>
	<div id="note-dialog-id" class="mdl-cell note-dialog">
		<div class="note-con mdl-card mdl-card mdl-shadow--16dp">
			<span style="display: none;" id="note-main-dialog-tmpnote-id"></span>
			<span style="display: none;" id="note-main-dialog-tmp-id"></span>		
			<div class="note-con-title mdl-card__title">
				<div class="mdl-card__title-text note-con-title-tmp"><spring:message code="ui.dashboard.common.div.title"/></div>
				<div spellcheck="true" tabindex="0" 
					contenteditable="true"
					 id="commonNoteTitle"
					class="note-con-title-text mdl-card__title-text"></div>
			</div>
			
			<div class="note-con-text">
				<div class="note-con-text-content-tmp"><spring:message code="ui.dashboard.common.div.content"/></div>
				<div spellcheck="false" tabindex="0" 
					id="commonNoteTextContent" contenteditable="true"
					onblur="changeTitleCont(this)" onfocus="changeTitleCont(this)"
					onkeyup="changeTitleCont(this)"
					class="note-con-text-content"></div>
			</div>
			
			<div id="note-dialog-main_acns-id" class="note-con-action mdl-card__actions mdl-shadow--16dp">
				<div class="note-con-action-contents">
					<div class="note-action-btns-div">
						
						<%-- <div id="note-action-btn-icon_bookmark" class="note-action-btn-icons">
						  <i class="material-icons">bookmark_border</i>
						</div>
						<span class="tooltip mdl-tooltip mdl-tooltip--top" for="note-action-btn-icon_bookmark">Bookmark</span>
						
						<div id="note-action-btn-icon_colors" class="note-action-btn-icons">
						  <i class="material-icons">color_lens</i>
						</div>
						<span class="tooltip mdl-tooltip mdl-tooltip--top" for="note-action-btn-icon_colors">Colors</span>
						
						<div id="note-action-btn-icon_event" class="note-action-btn-icons">
						  <i class="material-icons">event</i>
						</div>
						<span class="tooltip mdl-tooltip mdl-tooltip--top" for="note-action-btn-icon_event">Remainder</span> --%>
						<div id="note-action-btn-icon_info" class="note-action-btn-icons">
						  <i class="material-icons">error_outline</i>
						</div>
						<span class="tooltip mdl-tooltip mdl-tooltip--top" for="note-action-btn-icon_info"><spring:message code="ui.dashboard.common.div.created.on"/>: {{currentTimeNDate | date:'dd-MMM-yyyy HH:mm:ss'}}</span>
						<div id="note-action-btn-icon_delete" ng-click="skipNewNote()" class="note-action-btn-icons">
						  <i class="material-icons">delete</i>
						</div>
						<span class="tooltip mdl-tooltip mdl-tooltip--top" for="note-action-btn-icon_delete"><spring:message code="ui.dashboard.common.div.delete"/></span>
						<div class="note-ok-btn-div">
							<button id="note-dialog-ok-btn-id" 
								ng-click="addNewNote()" tabindex="0" 
								class="note-ok-btn mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect"><spring:message code="ui.dashboard.common.div.ok"/></button>
						</div>
					</div>
				</div>
			</div>
						
		</div>
	</div>
	<div id="note-dialog-overlay" class="note-dialog-overlay"></div>
<%-- Main Note Dialog End --%>

<%------------------------------------ Common Note Div Start ----------------------------------------%>	
	<div draggable="true" 
			ng-repeat="noteObj in noteList track by $index" 
			ng-if="noteObj.noteInd == 1" 
			class="mdl-cell mdl-cell--4-col note-common-div">
		<div node-index="{{$index}}" note-id="{{noteObj.noteId}}"
				style="{{noteObj.noteBgColor}}"	
				selected-div="(noteObj.isSelected==true)?'selected':'none'" 
				ng-class="(noteObj.isSelected==true)?
				'note-common-con mdl-shadow--2dp mdl-card note-selected':'note-common-con mdl-shadow--2dp mdl-card'"
				ng-mouseenter="comnNoteHoverInOut($index)" ng-mouseleave="comnNoteHoverInOut($index)" 
				id="cmnDivNote_{{$index}}">
		
			<div ng-click="showModelNoteContent($index,noteObj.noteId)" 
				class="note-con-title note-common-title mdl-card__title">
				<div spellcheck="true" tabindex="0" 
					contenteditable="false" id="cmnNoteTitle_{{$index}}"
					class="note-con-title-text mdl-card__title-text"
					ng-bind-html="noteObj.tmpTitle" 
					tmp-data="{{noteObj.noteTitle}}"
					></div>
			</div>
			<div ng-click="showModelNoteContent($index,noteObj.noteId)"
					 ng-mouseenter="comnAllAcnHoverOut($index)"
						class="note-con-text note-common-text">
				<div spellcheck="false" tabindex="0" 
					id="cmnNoteTextContent_{{$index}}"
					contenteditable="false"
					class="note-con-text-content"
					ng-bind-html="noteObj.noteTextContent"></div>
			</div>
			<!-- <div class="note-common-action mdl-card__actions" ng-style="noteObj.isHovered?{display:'block'}:{display:'none'}"> -->
			<div ng-mouseenter="comnAllAcnHoverOut($index)" 
				style="{{noteObj.noteBgColor}}" 
				class="note-common-action mdl-card__actions">
				<div class="note-con-action-contents">
					<div class="note-action-btns-div">
						<div id="note-btn-icn_bookmark_{{$index}}" ng-click="showComnNoteTag($index,$event)" class="note-action-btn-icons">
						  <i class="material-icons note-common-action-icons">bookmark_border</i>
						</div>
						<span class="tooltip mdl-tooltip mdl-tooltip--top" for="note-btn-icn_bookmark_{{$index}}"><spring:message code="ui.dashboard.common.scndry.div.bookmark"/></span>
						<div id="note-btn-icn_event_{{$index}}"  class="note-action-btn-icons mdl-datepicker__input note-action-cal-btn">
						  <i class="material-icons note-common-action-icons">event</i>
						</div>
						<span class="tooltip mdl-tooltip mdl-tooltip--top" for="note-btn-icn_event_{{$index}}"><spring:message code="ui.dashboard.common.scndry.div.remainder"/></span>
						<div id="note-btn-icn_info_{{$index}}" class="note-action-btn-icons">
						  <i class="material-icons note-common-action-icons">error_outline</i>
						</div>
						<span class="tooltip mdl-tooltip mdl-tooltip--top" for="note-btn-icn_info_{{$index}}">
							<b><spring:message code="ui.dashboard.common.scndry.div.created"/>:&nbsp;</b>{{noteObj.noteCrdate}}<br>
							<b><spring:message code="ui.dashboard.common.scndry.div.updated"/>:&nbsp;</b>{{noteObj.noteUpdate}}<br>
							<b><spring:message code="ui.dashboard.common.scndry.div.lastdel"/>:&nbsp;</b>{{noteObj.noteDeldate}}
						</span>
						<div id="note-btn-icn_colors_{{$index}}" ng-click="showNoteColor($index,$event)" class="note-action-btn-icons">
						  <i class="material-icons note-common-action-icons">{{noteObj.colorShow?'colorize':'color_lens'}}</i>
						</div>
						<span class="tooltip mdl-tooltip mdl-tooltip--top" for="note-btn-icn_colors_{{$index}}"><spring:message code="ui.dashboard.common.scndry.div.colors"/></span>
						<div node-index="{{$index}}" ng-click="deleteNoteDiv($index)" id="note-btn-icn_delete_{{$index}}" class="note-action-btn-icons">
						  <i class="material-icons note-common-action-icons">delete</i>
						</div>
						<span class="tooltip mdl-tooltip mdl-tooltip--top" for="note-btn-icn_delete_{{$index}}"><spring:message code="ui.dashboard.common.div.delete"/></span>
						<div node-index="{{$index}}" id="note-btn-icn_check_{{$index}}" ng-click="checkNoteDiv($index)" class="note-check-btn note-action-btn-icons">
						  <i id="note-btn-icn_check_icon_{{$index}}" 
						  	  class="material-icons note-check-common-icon note-common-action-icons">
						  	  {{(noteObj.isSelected==true)?'done_all':'done'}}</i>
						</div>
						<span class="tooltip mdl-tooltip mdl-tooltip--top" for="note-btn-icn_check_{{$index}}"><spring:message code="ui.dashboard.common.scndry.div.select"/></span>
					</div>
				</div>
			</div>			
		</div>
		<div id="note-cmn-tag-div_{{$index}}" 
				style="{{noteObj.noteBgColor}}"
				 
				ng-mouseenter="comnNoteTagHoverIn($index)"
				ng-class="(noteObj.tagShow)?'display-show note-acn-support-tag mdl-shadow--8dp':
													'display-hide note-acn-support-tag mdl-shadow--8dp'" >
		  <div class="display-inline-block">
		  	<div class="note-cmn-tag-itms">
		    	<input class="note-cmn-tag-txt note-cmn-tag-new-inp" type="text" 
		    	ng-keydown="checkInsertCmnNoteTagItms($index,$event,'true')" id="note-cmn-tag-new-inp_{{$index}}">
		    </div>
		    <div class="note-cmn-tag-itms note-cmn-tag-itms-icn" ng-click="checkInsertCmnNoteTagItms($index,$event,'false')">
		  		<i class="material-icons">add</i>
		  	</div>
		  </div>
		  <div ng-repeat="tagItm in noteObj.tagItems" class="display-inline-block">
		    <div class="note-cmn-tag-itms">
		    	<input class="note-cmn-tag-txt" value="{{tagItm}}" type="text" id="note-cmn-tag-inp_{{$index}}">
		    </div>
		    <div class="note-cmn-tag-itms note-cmn-tag-itms-icn ">
		    	<i 
		    		ng-mouseenter="checkCmNoteTagClear($event)" 
		    		ng-mouseleave="checkCmNoteTagClear($event)"
		    		ng-click="removeTagItems($parent.$index,$index)" 
		    		class="material-icons">remove_circle_outline</i>
		    </div>
		  </div>
		</div>
		
		<div id="note-cmn-colors-div_{{$index}}"  
				ng-mouseleave="comnNoteColorHoverOut($index)" ng-mouseenter="comnNoteColorHoverIn($index)" 
				ng-class="(noteObj.colorShow)?'display-show note-acn-support-colors mdl-shadow--8dp':
													'display-hide note-acn-support-colors mdl-shadow--8dp'" >
					<div class="note-choose-colors" ng-click="setNoteDivColor($index, 'white')" tabindex="0">
						<svg class="colors-svg" height="25" width="25">
						  <circle class="white-svg" cx="12" cy="12" r="10"  fill="{{colors.white}}" /> white  
						</svg>
					</div>
					<div class="note-choose-colors" ng-click="setNoteDivColor($index, 'blue')" tabindex="0">
						<svg class="colors-svg" height="25" width="25">
						  <circle cx="12" cy="12" r="10"  fill="{{colors.blue}}" /> blue  
						</svg>
					</div>
					<div class="note-choose-colors" ng-click="setNoteDivColor($index, 'green')" tabindex="0">
						<svg class="colors-svg" height="25" width="25">
						  <circle cx="12" cy="12" r="10"  fill="{{colors.green}}" /> green  
						</svg>
					</div>
					<div class="note-choose-colors" ng-click="setNoteDivColor($index, 'cyan')"  tabindex="0">
						<svg class="colors-svg" height="25" width="25">
						  <circle cx="12" cy="12" r="10"  fill="{{colors.cyan}}" /> cyan  
						</svg>
					</div>
					<div class="note-choose-colors" ng-click="setNoteDivColor($index, 'red')" tabindex="0">
						<svg class="colors-svg" height="25" width="25">
						  <circle cx="12" cy="12" r="10"  fill="{{colors.red}}" /> red  
						</svg>
					</div>
					<div class="note-choose-colors" ng-click="setNoteDivColor($index, 'grey')" tabindex="0">
						<svg class="colors-svg" height="25" width="25">
						  <circle cx="12" cy="12" r="10"  fill="{{colors.grey}}" /> grey  
						</svg>
					</div>
			</div>
	</div>	
	
<%------------------------------------ Common Note Div End ----------------------------------------%>



</div>
</div>
</main>

<%-- DashBoard Footer --%>
<jsp:include page="dashboard_footer_scripts.jsp" />
<script src="res/js/dashboard.js"></script>

<jsp:include page="dashboard_footer.jsp" />