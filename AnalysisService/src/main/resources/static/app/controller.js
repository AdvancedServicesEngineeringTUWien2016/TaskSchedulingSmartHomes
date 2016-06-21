//creates the module with the name 'menuApp'
var menuApp = angular.module('menuApp', ['ngRoute', 'userService', 'ui.bootstrap', 'tc.chartjs']);

menuApp.config(['$routeProvider', function($routeProvider) {
	$routeProvider
    	.when('/', {
    		templateUrl: '/pages/setup.html',
    		controller : 'setupController'
    	})
    	.when('/task/', {
    		templateUrl: 'pages/task.html',
    		controller : 'taskController'
    	})
    	.when('/newTask/', {
    		templateUrl: 'pages/newTask.html',
    		controller : 'taskController'
    	})
		.when('/statistics/', {
    		templateUrl: 'pages/statistics.html',
    		controller : 'statisticsController'
    	})
    	.when('/login/', {
    		templateUrl: 'pages/login.html',
    		controller : 'mainController'
    	})
    	.when('/register/', {
    		templateUrl: 'pages/register.html',
    		controller : 'mainController'
    	})
    	.when('/info/', {
    		templateUrl: 'pages/userInfo.html',
    		controller : 'infoController'
    	})
    	
		.otherwise('/');
}]);   

// ########################## Controller configuration
// ##########################

menuApp.controller('mainController', function($rootScope, $location, $route, $scope, $http, userService, $timeout) {
	$scope.user = "";
	
	$rootScope.error = false;
	$rootScope.info = false;
	$rootScope.errorMessage = "";
	$rootScope.infoMessage = "";
	
	$rootScope.fakeDateTime = 0;
	
		
	$rootScope.setError = function(errorMsg) {
		$rootScope.error = true;
		$rootScope.errorMessage = errorMsg;
		$timeout(function () { $rootScope.error = false; }, 5000);   
	}

	$rootScope.setInfo = function(infoMsg) {
		$rootScope.info = true;
		$rootScope.infoMessage = infoMsg;
		$timeout(function () { $rootScope.info = false; }, 5000);   
	}
	

    		
	$http.get('/getInfo')
		.success(function(result){
			console.log(result);
			$scope.user = result
		

	 		if($scope.user  == "")
	 			$location.path('/login');
		})
		.error(function(result){
			
		});
    		
		
	
	
	
//	$http.get('/getFakeDate').success(function(data) {
//		$rootScope.fakeDateTime = data;
//		console.log(data);
//		
//	});
	(function tick() {
		
			$http.get('/getFakeDate').success(function(data) {
				$rootScope.fakeDateTime = data;
				console.log(data);
				$timeout(tick, 10000);
			});
		
    })();
	
	$scope.login = function() {
   
		userService.login({username: $scope.username, password: $scope.password})
		.then(function() {
		
		}, function(error){
			$rootScope.setError("Login failed!");
			
		});
		
	};
	
		
    $scope.register = function(){
    	
    	var data = {
			username : $scope.username,
			password : $scope.password
			
		};

		console.log(data);
		
		$http.post('/register/', data)
			.success(function(result) {
				userService.login(data)
				$rootScope.setInfo(result.data);
			}).error(function(result) {
				$rootScope.setError(result.data);
			});
    }
    
    $scope.logout = function(){
    	userService.logout()

    }
    
   
});

