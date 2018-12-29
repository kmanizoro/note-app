'use strict';
var dashApp = angular.module('dashboard-app',['ngSanitize']);
try{
	dashApp.controller('dash-main-ctrl',['$scope','$http','$interval','dashboardService',function($scope,$http,$interval,dashboardService){
		
		$scope.cmNoteTitle = '';
		$scope.cmNoteContent = '';
		$scope.currentTimeNDate = new Date();
		
		$interval(function(){
			$scope.currentTimeNDate = new Date();
		},1000);
		
		$scope.colors = {
				white : '#FAFAFA',blue:'#10ADF5',green:'#CCFF90',cyan:'#65B6AE',red:'#EF9A9A',grey:'#CFD8DC'
		}
		$scope.checkAllNoteDivs = false;
		$scope.noteList = [];

		getDashDetails();
		
		$scope.addNewNote = function(){
			var lastIndex = ($scope.noteList.length>0)? $scope.noteList[$scope.noteList.length-1].noteId : 1000;
			var notContentEle = document.getElementById('commonNoteTextContent');
			var notTileEle = document.getElementById('commonNoteTitle');
			var tmpId = document.getElementById('note-main-dialog-tmpnote-id');
			var tmpIdX = document.getElementById('note-main-dialog-tmp-id');
			var noteTitleTxt = notTileEle.innerHTML;	var noteContentTxt = notContentEle.innerHTML;	
			var checkEditMode = checkEmptyNoteContent(noteTitleTxt,noteContentTxt);
			if(checkEditMode && 
				(tmpId.innerHTML=='' || tmpId.innerHTML.trim()=='') &&
					(tmpIdX.innerHTML=='' || tmpIdX.innerHTML.trim()=='')){
				noteContentTxt = noteContentTxt.replace('<br>', '');
				var title = (noteTitleTxt.length>10)?noteTitleTxt.substring(0,10)+"...":noteTitleTxt;
				var randomColor = getRandomColors();
				$scope.noteList.push({
					"noteId": (parseInt(lastIndex)+1),
					"noteInd": 1,
					"newObj": true,
					"noteTitle": title,
					"tmpTitle": noteTitleTxt,
					"noteTextContent": noteContentTxt,
					"isSelected": false,
					"noteCrdate":getCurrDateAsString(),
					"noteUpdate":getCurrDateAsString(),
					"noteDeldate" : "",
					"noteResdate" : "",
					"tagItems":[],
					"loginDetail" : document.getElementById('ssnLoginId').value,
					"userDetail": document.getElementById('ssnUserId').value,
					"tagShow" : false,
					"calShow" : false,
					"colorShow": false,
					"noteBgColor" : "background-color:"+randomColor.color+";",
					"noteColor" : randomColor.colorName,
		        	"isHovered": false
					});
				if(document.getElementById('dash_brd_icon_switch').checked){
					$scope.saveSingleNoteDivAng($scope.noteList.length-1);
				}
				console.log("id:"+(parseInt(lastIndex)+1)+" tmpTitle:"+title+" name:"+noteTitleTxt+" content:"+noteContentTxt);
			} else if(checkEditMode &&
						(tmpId.innerHTML!='' || tmpId.innerHTML.trim()!='') &&
							(tmpIdX.innerHTML!='' || tmpIdX.innerHTML.trim()!='')){
				var noteId = tmpId.innerHTML.trim();
				var noteIndex = tmpIdX.innerHTML.trim();
				if($scope.noteList.length>noteIndex &&
						$scope.noteList[noteIndex].noteId==noteId){
					noteContentTxt = noteContentTxt.replace('<br>', '');
					var title = noteTitleTxt.substring(0,10)+"...";
					$scope.noteList[noteIndex].tmpTitle=title;
					$scope.noteList[noteIndex].noteTitle=noteTitleTxt;
					$scope.noteList[noteIndex].noteTextContent=noteContentTxt;
					if(document.getElementById('dash_brd_icon_switch').checked){
						$scope.saveSingleNoteDivAng(noteIndex);
					}
				}
			}
			
			hideMainDialog();
		}
		
		$scope.skipNewNote=function(){
			
			hideMainDialog();
		}
		
		$scope.setDialogNoteDivColor=function(col){
			var dialogMain = document.getElementById('note-dialog-id');
			var dialogAcns = document.getElementById('note-dialog-main_acns-id');
			dialogMain.style.backgroundColor = ''+$scope.colors[col]+';';
			dialogAcns.style.backgroundColor = ''+$scope.colors[col]+';';
		}
		
		$scope.checkNoteDiv=function(index){
			var selIcon = document.getElementById('note-btn-icn_check_icon_'+index);
			selIcon.innerText = selIcon.innerText=='done'?'done_all':'done';
			if($scope.noteList.length>index){
				$scope.noteList[index].isSelected = $scope.noteList[index].isSelected?false:true;
			}
		}
		
		$scope.deleteNoteDiv=function(index){
			if($scope.noteList.length>=index){
				$scope.noteList[index].noteInd=0;
				$scope.deleteSingleNoteDivAng(index);
				showSnackMsg('Info ','1 Note Deleted',1);
			}
		}
		$scope.comnAllAcnHoverOut=function(index){
			$scope.noteList[index].tagShow=false;
			$scope.noteList[index].colorShow=false;
		}
		$scope.comnNoteHoverInOut=function(index){
			$scope.noteList[index].isHovered=($scope.noteList[index].isHovered)?false:true;
		}
		
		$scope.comnNoteColorHoverIn=function(index){ 
			$scope.noteList[index].colorShow=true;
			$scope.noteList[index].tagShow=false;
		}
		
		$scope.comnNoteColorHoverOut=function(index){ 
			$scope.noteList[index].colorShow=false; 
		}
		
		$scope.comnNoteTagHoverOut=function(index){ 
			$scope.noteList[index].tagShow=false; 
		}
		$scope.comnNoteTagHoverIn=function(index){ 
			$scope.noteList[index].tagShow=true; 
			$scope.noteList[index].colorShow=false;
		}
		
		$scope.showNoteColor=function(index,$event){
			$scope.noteList[index].colorShow=($scope.noteList[index].colorShow)?false:true;
			$scope.noteList[index].tagShow=false;
		}
		
		$scope.showComnNoteTag=function(index,$event){
			
			$scope.noteList[index].tagShow=($scope.noteList[index].tagShow)?false:true;
			$scope.noteList[index].colorShow=false;
			
		}
		
		$scope.setNoteDivColor=function(index,col){
			$scope.noteList[index].noteBgColor = "background-color:"+$scope.colors[col]+";";
			$scope.noteList[index].noteColor = col;
			if(document.getElementById('dash_brd_icon_switch').checked){
				$scope.saveSingleNoteDivAng(index);
			}
		}
		$scope.showTagInfo=function(index,$event){
			$scope.noteList[index].tagShow=($scope.noteList[index].tagShow)?false:true;
		}
		$scope.checkCmNoteTagClear=function($event){
			$event.currentTarget.innerHTML =  
				($event.currentTarget.innerHTML.indexOf("remove_circle_outline")==-1)? "remove_circle_outline" : "remove_circle";
		}
		$scope.checkInsertCmnNoteTagItms=function(index,$event,flag){
			var tagNewTxtInp = document.getElementById('note-cmn-tag-new-inp_'+index);
			var isExits = (tagNewTxtInp.value!='') && $scope.noteList[index].tagItems.indexOf(tagNewTxtInp.value.trim());
			var apIndex = ($scope.noteList[index].tagItems.length<=5) && (isExits===-1);
			var isSave = false;
			if(apIndex && flag=='true' && $event.which==13){
				$scope.noteList[index].tagItems.push(tagNewTxtInp.value);
				tagNewTxtInp.value="";
				isSave = true;
			} else if(apIndex && flag=='false'){
				$scope.noteList[index].tagItems.push(tagNewTxtInp.value);
				tagNewTxtInp.value="";
				isSave = true;
			}
			if(isSave && document.getElementById('dash_brd_icon_switch').checked){
				saveSingleNoteDivAng(index);
			}
		}
		
		$scope.removeTagItems=function(index,tagItmIndex){
			console.log("removeTagItems "+index,tagItmIndex);
			var tagItemList = $scope.noteList[index].tagItems;
			if(tagItemList!=null && typeof(tagItemList)!='undefined'){
				tagItemList.splice(tagItmIndex,1);
				if(document.getElementById('dash_brd_icon_switch').checked){
					saveSingleNoteDivAng(index);
				}
				showSnackMsg('Info ','Tag Deleted',1);
			}
		}
		
		$scope.checkAllNoteDivsFnAng=function(){
			$scope.checkAllNoteDivs = $scope.checkAllNoteDivs?false:true;
			if($scope.noteList.length>0){
				for(var i=0;i<$scope.noteList.length;i++){
					$scope.noteList[i].isSelected = $scope.checkAllNoteDivs;
				}
			}
		}
		
		$scope.saveAllNoteDivAng=function(){
			//showSnackMsg('Info ','Saved',1);
			var object = {
						"ListOfNoteObjects":$scope.noteList,
						"ssnUserId" : document.getElementById('ssnUserId').value,
						"ssnLoginSsnId" : document.getElementById('ssnLoginSsnId').value,
						"ssnLoginId" : document.getElementById('ssnLoginId').value
					};
			saveDashDetails(object);
		}
		
		$scope.saveSingleNoteDivAng=function(index){
			//showSnackMsg('Info ','Saved',1);
			var object = {
						"SingleNoteObject":$scope.noteList[index],
						"ssnUserId" : document.getElementById('ssnUserId').value,
						"ssnLoginSsnId" : document.getElementById('ssnLoginSsnId').value,
						"ssnLoginId" : document.getElementById('ssnLoginId').value
					};
			saveDashDetails(object);
		}
		
		$scope.deleteSingleNoteDivAng=function(index){
			//showSnackMsg('Info ','Saved',1);
			console.log("Index "+$scope.noteList[index].noteId);
			var object = {
						"DeleteNoteObjectSingle":$scope.noteList[index].noteId,
						"ssnUserId" : document.getElementById('ssnUserId').value,
						"ssnLoginSsnId" : document.getElementById('ssnLoginSsnId').value,
						"ssnLoginId" : document.getElementById('ssnLoginId').value
					};
			//console.log("Dele "+object);
			delDashboardDetails(object);
		}
		
		$scope.deleteAllSelectedNoteDivsFnAng=function(){
			var noteIdList = [];
			if($scope.noteList.length>0){
				for(var i=0;i<$scope.noteList.length;i++){
					if($scope.noteList[i].isSelected){
						$scope.noteList[i].noteInd = 0;
						noteIdList.push($scope.noteList[i].noteId);
					}
				}
			}
			if(noteIdList.length>0){
				var object = {
						"DeleteNoteObjectsList":noteIdList,
						"ssnUserId" : document.getElementById('ssnUserId').value,
						"ssnLoginSsnId" : document.getElementById('ssnLoginSsnId').value,
						"ssnLoginId" : document.getElementById('ssnLoginId').value
					};
				delDashboardDetails(object);
			}
			//showSnackMsg('Info ','Selected Items Deleted',1);
		}
		
		$scope.showModelNoteContent = function(index,noteId){
			var cont = document.getElementById('commonNoteTextContent');
			var titl = document.getElementById('commonNoteTitle');
			var tmpId = document.getElementById('note-main-dialog-tmpnote-id');
			var tmpIdx = document.getElementById('note-main-dialog-tmp-id');
			cont.innerHTML = $scope.noteList[index].noteTextContent;
			titl.innerHTML = $scope.noteList[index].noteTitle;
			tmpId.innerHTML = noteId; tmpIdx.innerHTML = index;
			changeTitleCont(cont);changeTitleCont(titl);
			showMainDialog();
		}
		
		function saveDashDetails(details){
			dashboardService.saveDashboardDetails(details)
	            .then(
	            function(d) {
	            	if(d.status=='200'){
	            		showSnackMsg('Info', 'Notes Saved Successfully',1);
	            	}
	            },
	            function(errResponse){
	                console.error('Error in saveProfileDetails Controller while fetching Users');
	            }
	        );
	    }
		
		function delDashboardDetails(request){
			dashboardService.deleteDashboardDetails(request)
            .then(
            function(d) {
            	if(d.status=='200'){
            		showSnackMsg('Info', 'Note(s) Deleted Successfully',1);
            	}
            },
            function(errResponse){
                console.error('Error in deleteDashboardDetails Controller while fetching Users');
            }
           );
		}
		
		function getDashDetails(){
			
			var reqParams = {
					"ssnUserId" : document.getElementById('ssnUserId').value,
					"ssnLoginSsnId" : document.getElementById('ssnLoginSsnId').value,
					"ssnLoginId" : document.getElementById('ssnLoginId').value	
				};
			
			dashboardService.getDashboardDetails(reqParams)
	            .then(
	            function(d) {
	            	console.log(d);
	            	if(d.status=='200'){
	            		$scope.noteList = d.data;
	            		console.log("Log "+d.data);
	            		//showSnackMsg('Info', 'Notes Saved Successfully',1);
	            	}
	            },
	            function(errResponse){
	                console.error('Error in getDashboardDetails Controller while fetching Users'+errResponse);
	            }
	        );
	    }
		
	}]);
	
	dashApp.factory('dashboardService',['$http','$q',function($http,$q){
		var appUrl = window.location.origin;
		var GET_DASHBOARD_DETAILS = appUrl + '/api/getDashboard/';
		var SAVE_DASHBOARD_DETAILS = appUrl + '/api/saveDashboard/';
		var DEL_DASHBOARD_DETAILS =  appUrl + '/api/deleteDashboard/';
		
		var factory = {
				getDashboardDetails : getDashboardDetails,
				saveDashboardDetails : saveDashboardDetails,
				deleteDashboardDetails : deleteDashboardDetails
		};
		return factory;
		
		// getDashboardDetails
		function getDashboardDetails(req){
			var def = $q.defer();
			console.log("GET_DASHBOARD_DETAILS"+GET_DASHBOARD_DETAILS);
			$http.post(GET_DASHBOARD_DETAILS,req).then(
				function(response){
					def.resolve(response);
				},
				function(errResponse){
					console.log("Error In getDashboardDetails: Error Response");
					def.reject(errResponse);
				}
			);
			return def.promise;
		}
		
		function saveDashboardDetails(details){
			var def = $q.defer();
			console.log("details "+details);
			$http.post(SAVE_DASHBOARD_DETAILS,details).then(
				function(response){
					def.resolve(response);
				},
				function(errResponse){
					console.log("Error In Factory saveDashboardDetails: Error Response");
					def.reject(errResponse);
				}
			);
			return def.promise;
		}
		
		function deleteDashboardDetails(details){
			var def = $q.defer();
			console.log("details "+details);
			$http.post(DEL_DASHBOARD_DETAILS,details).then(
				function(response){
					def.resolve(response);
				},
				function(errResponse){
					console.log("Error In Factory deleteDashboardDetails: Error Response");
					def.reject(errResponse);
				}
			);
			return def.promise;
		}
	}]);
	
}catch(ex){
	console.log(ex.message);
}

	var modal = document.getElementById('note-dialog-id');
	//var okBtn = document.getElementById('note-dialog-ok-btn-id');
	var delBtn = document.getElementById('note-action-btn-icon_delete');
	document.querySelector('#dash_brd_icon_add').addEventListener('click',showMainDialog,false);
	//okBtn.addEventListener('click',hideMainDialog,false);
	delBtn.addEventListener('click',hideMainDialog,false);
	window.onclick = function(event) {
	    if (event.target == modal) {
	    	hideMainDialog();
	    }
	}
	window.onload=function(){
		var wid = document.body.offsetWidth || document.body.clientWidth;
		var hig = document.body.offsetHeight || document.body.clientHeight;
		modal.style.width=wid+"px"; modal.style.height=hig+"px";
	}
	function hideMainDialog(){
		console.log("model Closed");
		modal.style.display = "none";
        modal.className = modal.className.replace(" note-con-show","");
        var cont = document.getElementById('commonNoteTextContent');
		var titl = document.getElementById('commonNoteTitle');
		var tmpId = document.getElementById('note-main-dialog-tmpnote-id');
		var tmpIdx = document.getElementById('note-main-dialog-tmp-id');
		cont.innerHTML = ''; titl.innerHTML = '';
		tmpId.innerHTML = ''; tmpIdx.innerHTML = '';
		changeTitleCont(cont);changeTitleCont(titl);
	}
	function showMainDialog(){
		console.log("model Opened");
		modal.className = modal.className + " note-con-show";
		modal.style.display = "block";
		document.getElementById('commonNoteTitle').focus();
	}
	
	var txtTitleElement = document.getElementById('commonNoteTitle');
	var txtContentElement = document.getElementById('commonNoteTextContent');
	txtTitleElement.addEventListener('keypress', function(e){
		this.previousElementSibling.style.display = "none";
		var evt = e || window.event; var keyCode = (evt.which?evt.which:evt.keyCode);
		if(keyCode==13){ txtContentElement.focus();	}
	}, false);

	document.addEventListener('keydown', function(event){
		var evt = event || window.event; var keyCode = (evt.which?evt.which:evt.keyCode); console.log(keyCode);
		
		if(keyCode==27){
			evt.preventDefault ? evt.preventDefault() : evt.returnValue = false
			console.log("Escape Pressed");
			hideMainDialog();
			showSnackMsg('Skip ','Close All Modals',1);
			return false;
		}
		
		if((evt.ctrlKey || evt.metaKey) && evt.shiftKey && keyCode==65){
			evt.preventDefault ? evt.preventDefault() : evt.returnValue = false
			console.log("CTRL + Shift +A");
			checkAllNoteDivsFn();
			showSnackMsg('Select All Note ','CTRL + SHIFT + A',1);
			return false;
		}
		
		if((evt.ctrlKey || evt.metaKey) && evt.shiftKey && keyCode==70){
			evt.preventDefault ? evt.preventDefault() : evt.returnValue = false;
			document.getElementById('commonSearch').focus();
			showSnackMsg('Find ','Please Enter String',1);
			return false;
		}
		
		if((evt.ctrlKey || evt.metaKey) && evt.shiftKey && keyCode==83){
			evt.preventDefault ? evt.preventDefault() : evt.returnValue = false;
			saveAllNoteDiv();
			return false;
		}
		
		if((evt.ctrlKey || evt.metaKey) && evt.shiftKey && keyCode==66){
			evt.preventDefault ? evt.preventDefault() : evt.returnValue = false;
			showSnackMsg('New Note ','CTRL + SHIFT + B',1);
			showMainDialog();
			return false;
		}
		
		if(keyCode==46){
			evt.preventDefault ? evt.preventDefault() : evt.returnValue = false;
			console.log("Delete Key");
			showSnackMsg('Deleted ','Delete Key',1);
			return false;
		}
	}, false);

	document.getElementById('dash_brd_icon_autosave').addEventListener('click', showHideAutoSave,false);
	document.getElementById('dash_brd_icon_switch').addEventListener('click', showHideAutoSave,false);
	
	txtTitleElement.addEventListener('blur', function(e){
		var txt = this.innerHTML.trim();if(txt.length==0 || txt==''){ this.previousElementSibling.style.display = "block";}else{this.previousElementSibling.style.display = "none";}
	}, false);

	function showHideAutoSave(){
		var ele = document.getElementById('dash_brd_icon_save');
		ele.style.display= (ele.style.display=='none')?'block': 'none';
	}
	
	function checkAllNoteDivsFn(){
		angular.element(document.getElementById('dash-main-app-id')).scope().checkAllNoteDivsFnAng();
		var clearIcn = document.getElementById('dash_brd_icon_check_icon');
		clearIcn.innerHTML = (clearIcn.innerHTML=='clear')? 'done_all' : 'clear';
	}
	
	function saveAllNoteDiv(){
		angular.element(document.getElementById('dash-main-app-id')).scope().saveAllNoteDivAng();
	}
	
	function deleteAllNoteSelDivs(){
		angular.element(document.getElementById('dash-main-app-id')).scope().deleteAllSelectedNoteDivsFnAng();
	}
	
	function changeTitleCont(ele){
		var txt = ele.innerHTML.trim();if(txt.length==0 || txt==''){ ele.previousElementSibling.style.display = "block";}else{ele.previousElementSibling.style.display = "none";}
	}

	function formatNoteContent(txt){
		return txt.replace(new RegExp('<br>','g'),'/n').replace(new RegExp('<div>','g'),'').replace(new RegExp('</div>','g'),'/n');
	}

	function checkEmptyNoteContent(title,content){
		if(title==null && content==null){
			return false;
		}
		if((title=='' && content=='') ||
				(title.replace(new RegExp('<br>','g'),'').trim()=='' && content.replace(new RegExp('<br>','g'),'').trim()=='') ||
						(title.replace(new RegExp('<div>','g'),'').replace(new RegExp('<div>','g'),'').trim()=='' 
								&& content.replace(new RegExp('</div>','g'),'').replace(new RegExp('</div>','g'),'').trim()=='')){
			return false;
		}
		return true;
	}