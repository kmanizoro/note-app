var loginApp = angular.module('login-app',[]);
try{
	loginApp.controller('lang-controller',function($scope){
		$scope.goToLogin = function(language){
			window.location.href = window.location.pathname+"?lang="+language;
		}
	});
}catch(err){
	console.log(err.message);
	showSnackMsg('COMN001',err.message);
}

window.onload = function(){
	var errEle = document.getElementById('MSG_ERR');if(errEle && errEle.value !=''){showSnackMsg('Error', errEle.value);}
	var sussEle = document.getElementById('SuccessId');if(sussEle && sussEle.value !=''){showSnackMsg('Info', sussEle.value);}
	var sussEle = document.getElementById('RES_INFO_Id');if(sussEle && sussEle.value !=''){showSnackMsg('Info', sussEle.value);}
}