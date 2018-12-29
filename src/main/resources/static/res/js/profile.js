'use strict';
var profileApp = angular.module('profile-app',[]);
try{
	'use strict';
	profileApp.controller('psrnl-ctrl',['$scope','psrnlService', function($scope,psrnlService){
		//scope variables
		$scope.inputType = 'password';
		$scope.inputType2 = 'password';
		$scope.showHideVisbilityIcon = 'visibility_off';
		$scope.showHideVisbilityIcon2 = 'visibility_off';
		$scope.editCloseIcon = 'edit';
		$scope.isShowUpdatePassFld = false;
		$scope.isShowSaveBtn = true;
		$scope.isShowUpdateBtn = true;
		var self = this;
		$scope.user = {
				userName :'Test',
				userPass:'Test',
				usernewpass:'Test',
				userMail:'Test@Test.com',
				userDisplayname:'Test',
				userFirstname:'Test',
				userLastname:'Test',
				userGender:'Test',
				userDob :new Date('2001/01/01'),
		};
		
		//scope functions
		$scope.showHidePassFld = function(){
			if($scope.inputType=='password'){
				$scope.inputType = 'text';
				$scope.showHideVisbilityIcon = 'visibility';
			}else{
				$scope.inputType = 'password';
				$scope.showHideVisbilityIcon = 'visibility_off';
			}
		}
		$scope.showHidePassFld2 = function(){
			if($scope.inputType2=='password'){
				$scope.inputType2 = 'text';
				$scope.showHideVisbilityIcon2 = 'visibility';
			}else{
				$scope.inputType2 = 'password';
				$scope.showHideVisbilityIcon2 = 'visibility_off';
			}
		}
		$scope.showHideUpdatePassBlck = function(){
			$scope.isShowUpdatePassFld = $scope.isShowUpdatePassFld? false : true;
			$scope.editCloseIcon = $scope.isShowUpdatePassFld? 'clear' :'edit';
		}
		
		$scope.savePersonalDetails = function(){
			$scope.isShowSaveBtn = false;
			saveProfileDetails($scope.user);
			document.getElementById('userDispNameId').innerHTML = $scope.user.userDisplayname;
		}
		
		$scope.updatePersonalDetails = function(){
			$scope.isShowUpdateBtn = false;
			console.log("Before"+$scope.user);
			saveProfileDetails($scope.user);
		}
		
		var reqParams = {
				"ssnUserId" : document.getElementById('ssnUserId').value,
				"ssnLoginSsnId" : document.getElementById('ssnLoginSsnId').value,
				"ssnLoginId" : document.getElementById('ssnLoginId').value	
			};
		
		getProfileDetails(reqParams);
		
	    function getProfileDetails(request){
	    	psrnlService.getProfileDetails(request)
	            .then(
	            function(d) {
	            	$scope.user = formateRes(d.data);
	            	//$scope.user = d.data;
	            	console.log(d.data.userGender);
	            },
	            function(errResponse){
	                console.error('Error while fetching Users');
	            }
	        );
	    }
	    
	    function saveProfileDetails(user){
	    	psrnlService.saveProfileDetails(user)
	            .then(
	            function(d) {
	            	//$scope.user = formateRes(d.data);
	            	$scope.user = d.data;
	            	$scope.isShowSaveBtn = true;
	            	$scope.isShowUpdateBtn = true;
	            	if(d.status=='200'){
	            		showSnackMsg('Info', 'Profile Saved Successfully',1);
	            	}
	            },
	            function(errResponse){
	                console.error('Error in saveProfileDetails Controller while fetching Users');
	            }
	        );
	    }
	    
	    function formateRes(user){
	    	var dd=new Date();
	    	if(user.userDob !=null && user.userDob!=''){
	    		dd=new Date(parseInt(user.userDob));
	    		user.userDob=dd;
	    	}
	    	return user;
	    }
	}]);
	
	'use strict';
	profileApp.factory('psrnlService',['$http','$q',function($http,$q){
		var appUrl = window.location.origin;
		var GET_PERSONAL_DETAILS = appUrl + '/api/getProfile/';
		var PUT_PERSONAL_DETAILS = appUrl + '/api/saveProfile/';
		var factory = {
				getProfileDetails : getProfileDetails,
				saveProfileDetails : saveProfileDetails,
				delUserProfile : delUserProfile,
		};
		return factory;
		
		//get user details
		function getProfileDetails(req){
			var def = $q.defer();
			$http.post(GET_PERSONAL_DETAILS,req).then(
				function(response){
					def.resolve(response);
				},
				function(errResponse){
					console.log("Error In getProfileDetails: Error Response");
					def.reject(errResponse);
				}
			);
			return def.promise;
		}
		
		function saveProfileDetails(user){
			var def = $q.defer();
			$http.post(PUT_PERSONAL_DETAILS,user).then(
				function(response){
					def.resolve(response);
				},
				function(errResponse){
					console.log("Error In Factory saveProfileDetails: Error Response");
					def.reject(errResponse);
				}
			);
			return def.promise;
		}
		
		function delUserProfile(){
			var def = $q.defer();
			$http.get(GET_PERSONAL_DETAILS).then(
				function(response){
					def.resolve(response);
				},
				function(errResponse){
					console.log("Error In getProfileDetails: Error Response");
					def.reject(errResponse);
				}
			);
			return def.promise;
		}
		
	}]);
	
}catch(err){
	console.log(err.message);
	showSnackMsg('COMN001',err.message);
}
try{

	var dialog = document.querySelector('dialog');
	var showDialogButton = document.querySelector('#delete_account');
	var okButton = document.querySelector('#acc-del-confirm');
	if (! dialog.showModal) {
	  dialogPolyfill.registerDialog(dialog);
	}
	showDialogButton.addEventListener('click', function() {
	  dialog.showModal();
	});
	dialog.querySelector('.close').addEventListener('click', function() {
	  dialog.querySelector('#delcnfpassword').value="";
	  dialog.close();
	});
	okButton.addEventListener('click', function() {
		window.location.href = window.location.pathname;
	});

}catch(err){
	console.log(err.message);
	showSnackMsg('COMN001',err.message);
}

window.onload = function(){
	var errEle = document.getElementById('MSG_ERR');if(errEle && errEle.value !=''){showSnackMsg('Error', errEle.value);}
	var sussEle = document.getElementById('SuccessId');if(sussEle && sussEle.value !=''){showSnackMsg('Info', sussEle.value);}
}