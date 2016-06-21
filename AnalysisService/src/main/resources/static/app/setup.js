menuApp.controller('setupController', function($rootScope, $route, $scope, $http, $location, $window) {

	$scope.user = "";
	
	$scope.weatherAccuracy = 50;
	$scope.batteryLoad = 0;
	$scope.energyVariation = 45;
	$scope.accuracyThres = 55;
	$scope.maxEnergyProduction = 5000;
	$scope.weatherServiceIP = "localhost:8091";
	$scope.igPlusServiceIP = "localhost:8092";
	$scope.batteryServiceIP = null;
	$scope.batteryCapacity = 12000;
	$scope.automationServiceIP = "localhost:8094";
	$scope.priceServiceIP = "localhost:8093";
	
	
	$scope.setupIGcomplete = false;
	$scope.setupAnalysiscomplete = false;
	$scope.setupWeathercomplete = false;
	
	$scope.simulate = "";

	
	$scope.lat = "48.208824";
	$scope.lon = "16.348064";
	
	
	$(function() {
	    $( "#weatherAccuracy" ).slider({
	    	 range:true,
             min: 0,
             max: 100
             
	    });
	});
	
	$(function() {
	    $( "#batteryLoad" ).slider({
	    	 range:false,
             min: 0,
             max: 100
             
	    });
	});
	
	$(function() {
	    $( "#energyVariation" ).slider({
	    	 range:true,
             min: 0,
             max: 100
             
	    });
	});
	
	$(function() {
	    $( "#accuracyThres" ).slider({
	    	 range:false,
             min: 0,
             max: 100
	    });
	});
	
	$(function() {
	    $( "#accuracyThresAnal" ).slider({
	    	 range:false,
             min: 0,
             max: 100
	    });
	});
	


	$http.get('/current')
		.success(function(data){
			console.log(data);
			$scope.user = data;
			
			if($scope.user  == "")
				$location.path('/login');
		})
		.error(function(error){
			
		});
	
	$scope.setupIG = function(){
	
		var setupData = {
			maxEnergyProduction : $scope.maxEnergyProduction,
			weatherAccuracyThreshold : $( "#accuracyThres" ).slider( "option", "value" ),
			weatherServiceAddress : $scope.weatherServiceIP,
			igPlusServiceAddress : $scope.igPlusServiceIP,
			latitude : $scope.lat,
			longitude : $scope.lon,
			batteryCapacity : $scope.batteryCapacity,
			batteryLoad : $( "#batteryLoad" ).slider( "option", "value")
			
		};

		console.log(setupData);
		
		$http.post('/setupIG/', setupData)
			.success(function(result) {
				$rootScope.setInfo(result.data);
				console.log(result.data);
				$scope.setupIGcomplete = true;
			}).error(function(result) {
				$rootScope.setError(result.data);
			});
	}
	

	
	$scope.setupWeather = function(){
		console.log("sssssssssssssssssssssss: " , $scope.simulate);
		var setupData = {
			weatherServiceAddress : $scope.weatherServiceIP,
			weatherAccuracyMin :  $( "#weatherAccuracy" ).slider( "values", 0 ),
			weatherAccuracyMax :  $( "#weatherAccuracy" ).slider( "values", 1 ),
			simulate : $scope.simulate
		};

		console.log(setupData);
		
		$http.post('/setupWeather/', setupData)
			.success(function(result) {
				$rootScope.setInfo(result.data);
				console.log(result.data);
				$scope.setupWeathercomplete = true;
			}).error(function(result) {
				$rootScope.setError(result.data);
				
			});
	}
	
	$scope.PremiumUser = "no";
	
	$scope.setupAnalysis = function(){
		
		var setupData = {
			weatherServiceAddress : $scope.weatherServiceIP,
			weatherAccuracyThreshold : $( "#accuracyThres" ).slider( "option", "value" ),
			priceServiceAddress : $scope.priceServiceIP,
			igPlusServiceAddress : $scope.igPlusServiceIP,
			homeautomationAddress : $scope.automationServiceIP,
			latitude : $scope.lat,
			longitude : $scope.lon,
			premiumUser : $scope.PremiumUser
		};

		console.log(setupData);
		
		$http.post('/setupAnalysis/', setupData)
			.success(function(result) {
				$rootScope.setInfo(result.data);
				console.log(result.data);
				$scope.setupAnalysiscomplete = true;
			}).error(function(result) {
				$rootScope.setError(result.data);
			});
	}
	
menuApp.controller('setupController', function($rootScope, $route, $scope, $http, $location, $window) {

	$scope.user = "";
	
	$scope.weatherAccuracy = 50;
	$scope.batteryLoad = 0;
	$scope.energyVariation = 45;
	$scope.accuracyThres = 55;
	$scope.maxEnergyProduction = 2000;
	$scope.weatherServiceIP = "localhost:8091";
	$scope.igPlusServiceIP = "localhost:8092";
	$scope.batteryServiceIP = null;
	$scope.batteryCapacity = 25000;
	$scope.automationServiceIP = "localhost:8094";
	$scope.priceServiceIP = "localhost:8093";
	
	
	$scope.setupIGcomplete = false;
	$scope.setupAnalysiscomplete = false;
	$scope.setupWeathercomplete = false;

	
	$scope.lat = "48";
	$scope.lon = "48";
	
	
	
	
	$(function() {
	    $( "#weatherAccuracy" ).slider({
	    	 range:true,
             min: 0,
             max: 100
             
	    });
	});
	
	$(function() {
	    $( "#batteryLoad" ).slider({
	    	 range:false,
             min: 0,
             max: 100
             
	    });
	});
	
	$(function() {
	    $( "#energyVariation" ).slider({
	    	 range:true,
             min: 0,
             max: 100
             
	    });
	});
	
	$(function() {
	    $( "#accuracyThres" ).slider({
	    	 range:false,
             min: 0,
             max: 100
	    });
	});
	
	$(function() {
	    $( "#accuracyThresAnal" ).slider({
	    	 range:false,
             min: 0,
             max: 100
	    });
	});
	


	$http.get('/current')
		.success(function(data){
			console.log(data);
			$scope.user = data;
			
			if($scope.user  == "")
				$location.path('/login');
		})
		.error(function(error){
			
		});
	
	$scope.setupIG = function(){
	
		var setupData = {
			maxEnergyProduction : $scope.maxEnergyProduction,
			weatherAccuracyThreshold : $( "#accuracyThres" ).slider( "option", "value" ),
			productionVariationMin : $( "#energyVariation" ).slider( "values", 0 ),
			productionVariationMax : $( "#energyVariation" ).slider( "values", 1 ),
			weatherServiceAddress : $scope.weatherServiceIP,
			igPlusServiceAddress : $scope.igPlusServiceIP,
			latitude : $scope.lat,
			longitude : $scope.lon,
			batteryCapacity : $scope.batteryCapacity,
			batteryLoad : $( "#batteryLoad" ).slider( "option", "value")
			
		};

		console.log(setupData);
		
		$http.post('/setupIG/', setupData)
			.success(function(result) {
				$rootScope.setInfo(result.data);
				console.log(result.data);
				$scope.setupIGcomplete = true;
			}).error(function(result) {
				$rootScope.setError(result.data);
			});
	}
	

	
	$scope.setupWeather = function(){
		
		var setupData = {
			weatherServiceAddress : $scope.weatherServiceIP,
			weatherAccuracyMin :  $( "#weatherAccuracy" ).slider( "values", 0 ),
			weatherAccuracyMax :  $( "#weatherAccuracy" ).slider( "values", 1 )
		};

		console.log(setupData);
		
		$http.post('/setupWeather/', setupData)
			.success(function(result) {
				$rootScope.setInfo(result.data);
				console.log(result.data);
				$scope.setupWeathercomplete = true;
			}).error(function(result) {
				$rootScope.setError(result.data);
				
			});
	}
	
	$scope.setupAnalysis = function(){
		
		var setupData = {
			weatherServiceAddress : $scope.weatherServiceIP,
			weatherAccuracyThreshold : $( "#accuracyThres" ).slider( "option", "value" ),
			priceServiceAddress : $scope.priceServiceIP,
			igPlusServiceAddress : $scope.igPlusServiceIP,
			homeautomationAddress : $scope.automationServiceIP,
			latitude : $scope.lat,
			longitude : $scope.lon
		};

		console.log(setupData);
		
		$http.post('/setupAnalysis/', setupData)
			.success(function(result) {
				$rootScope.setInfo(result.data);
				console.log(result.data);
				$scope.setupAnalysiscomplete = true;
			}).error(function(result) {
				$rootScope.setError(result.data);
			});
	}});
});
