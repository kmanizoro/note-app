'use strict';
var supportApp = angular.module('support-app',[]);
try{
	'use strict';
	supportApp.controller('support-ctrl',['$scope','supportService', function($scope,supportService){
		
		$scope.isShowSubmitBtn = true;
		$scope.sendSupportDetailsFun=function(){
			$scope.isShowSubmitBtn=false;
			var data = {
					name:$scope.user,
					email:$scope.email,
					content:$scope.content
			};
			console.log('Support Details Start');
			sendDetails(data);
			console.log('Support Details End');
		}
		
		function sendDetails(support){
			supportService.sendSupportDetails(support).then(
				function(d){
					console.log("log"+d.data);
					if(d.status=='200'){
						showSnackMsg('Info', 'Mail Sent Successfully',1);
						$scope.isShowSubmitBtn=true;
						$scope.user='';
						$scope.email='';
						$scope.content='';
					}else{
						$scope.isShowSubmitBtn=true;
						showSnackMsg('Info', 'Mail Not Sent,Check all the details - Some Technical Issue',1);
					}
				},
				function(response){
					console.error("Error In Service "+response);
				}
			);
		}
		
	}]);
	
	'use strict';
	supportApp.factory('supportService',['$http','$q',function($http,$q){
		var appUrl = window.location.origin;
		var SEND_SUPPORT_DETAILS = appUrl + '/api/sendSupport/';
		var factory = {
				sendSupportDetails : sendSupportDetails
		};
		return factory;
				
		function sendSupportDetails(support){
			var def = $q.defer();
			$http.post(SEND_SUPPORT_DETAILS,support).then(
				function(response){
					def.resolve(response);
				},
				function(response){
					console.log("Error In Factory saveProfileDetails: Error Response"+response);
					def.reject(response);
				}
			);
			return def.promise;
		}
	}]);

	
} catch(err){
	console.log("Error-> "+err.message);
}
