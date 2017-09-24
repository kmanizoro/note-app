function showSnackMsg(msgCode,msgStr){
	showSnackMsg(msgCode,msgStr,'');
}
function showSnackMsg(msgCode,msgStr,msgTimout){
	'use strict';
	var snackbarContainer = document.querySelector('#msgSnackBar');
	msgTimout = (msgTimout==''||msgTimout==null)? 10000 : (msgTimout*1000); 
	var snackbarBtnAction = function(){
		snackbarContainer.style.opacity = "0";
	}
    'use strict';
    var data = {
    		message: msgCode+ '# '+msgStr,
    		timeout: msgTimout,
    		actionText: 'Dismiss',
    		actionHandler:snackbarBtnAction
    		};
    snackbarContainer.MaterialSnackbar.showSnackbar(data);
}
function getCurrDateAsString(){
   return ""+(new Date().toLocaleString())+"";	
}