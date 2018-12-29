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
function getRandomColors(){
	var num = Math.floor(Math.random() * 5) + 1;
	var appColors = '[{"colorName": "white", "color" : "#FAFAFA"},{"colorName":"blue" ,"color":"#10ADF5"}, {"colorName":"green" ,"color":"#CCFF90"}, {"colorName":"cyan" ,"color":"#65B6AE"}, {"colorName":"grey" ,"color":"#CFD8DC"}, {"colorName":"red" ,"color":"#EF9A9A"}]';
	var colorsInp = JSON.parse(appColors);
	return colorsInp[num];
}