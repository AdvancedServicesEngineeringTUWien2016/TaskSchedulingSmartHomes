menuApp.controller('infoController', function($rootScope, $location, $route, $scope, $http, userService) {
	$scope.user = "";
	$scope.userInfo = "";
	
	$scope.number = '';
	
	
	$http.get('/current')
    	.success(function(data){
    		console.log(data);
    		$scope.user = data;
    		
		})
    	.error(function(error){
    		
		});
	
	
	$http.get('/getInfo')
		.success(function(result){
			console.log(result);
			$scope.userInfo = result
			$scope.number = $scope.userInfo.battery.currentCapacity / $scope.userInfo.battery.maxCapacity *100;
			$scope.number += '%';
			console.log($scope.number);
		})
		.error(function(result){
			$rootScope.setError(result.data);
		});
	
});
