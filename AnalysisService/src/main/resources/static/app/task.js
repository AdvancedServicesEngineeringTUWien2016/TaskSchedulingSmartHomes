menuApp.controller('taskController', function($rootScope, $route, $scope, $http, $timeout) {

	$scope.deviceName = null;
	$scope.taskName = null;
	$scope.duration = 0;
	$scope.energyusage = 0;
	$scope.endpointHAS = null;
	$scope.startCommand = null;
	$scope.stopCommand = null;
	
	$scope.tasks = null;

	$http.get('/getInfo')
	.success(function(result){
		console.log(result);
		$scope.user = result
	
	})
	.error(function(result){
		
	});
		  
	(function tick() {
		
		$http.get('/getTasks/')
		.success(function(result) {
			$scope.tasks = result;
			console.log(result);
			
		}).error(function(result) {
			$rootScope.setError(result.data);
		});
//		$timeout(tick, 10000);
	
	})();
	
	$scope.newTask = function(){
		
		var data = {
			devicename : $scope.deviceName,
			homeAutomationEndpoint : $scope.endpointHAS,
			taskname : $scope.taskName,
			duration : $scope.duration,
			energyUsage : $scope.energyusage,
			startCommand : $scope.startCommand,
			stopCommand : $scope.stopCommand
		};

		console.log(data);
		
		$http.post('/registerTask/', data)
			.success(function(result) {
				$rootScope.setInfo(result.data);
				console.log(result.data);
			}).error(function(result) {
				$rootScope.setError(result.data);
			});
	}
	
	$scope.plan = function(task){
				
		var data = {
			taskId : task.taskId,
			earliestStartTime : task.earliestStartTime,
			latestFinishTime : task.latestFinishTime
		};
		$http.post('/planTask/', data)
			.success(function(result) {
				$rootScope.setInfo(result.data);
				console.log(result.data);
			}).error(function(result) {
				$rootScope.setError(result.data);
			});
		console.log(task);
	}
	
	

	$scope.open = function(task) {
		task.opened = true;
	};

	

	
	$scope.popup1 = {
		opened : false
	};
	
	$scope.parseDate = function(isoString) {
		if(isoString == null)
			return "";
		var date = new Date(isoString);
		var result = (date.getDate() + "." + (date.getMonth() + 1) + "." + date
				.getFullYear() + " " + date.getHours() + ":" + date.getMinutes());
		
		
		return result;
	}
	
	$scope.updateData = function(){
		$http.get('/getTasks/')
		.success(function(result) {
			$scope.tasks = result;
			console.log(result);
			
		}).error(function(result) {
			$rootScope.setError(result.data);
		});
	}
	
	 $scope.showStart = function(){
	    	
	    	console.log($scope.user);
	    	
	    	if($scope.user.igPlusServiceAddress == null || $scope.user.homeautomationAddress == null || $scope.user.energyPriceServiceAddress == null || $scope.user.battery == null
	    			|| $scope.user.latitude == null || $scope.user.longitude == null || $scope.user.weatherServiceAddress == null){
	    		return false;
	    		
	    	}
	    	else
	    		return true;
	    
	    }
		
	    $scope.startScheduling = function(){
	    	$http.get('/startTick/')
	    		.success(function(result) {
	    			$rootScope.setInfo(result.data);
	    			console.log(result);
				
			}).error(function(result) {
				$rootScope.setError(result.data);
				
			});
	    }

		$scope.stopScheduling = function(){
			$http.get('/stopTick/')
				.success(function(result) {
					$rootScope.setInfo(result.data);
					console.log(result);
				
			}).error(function(result) {
				$rootScope.setError(result.data);
				
			});
		}

	
});
