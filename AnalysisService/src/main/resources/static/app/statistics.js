menuApp
		.controller(
				'statisticsController',
				function($rootScope, $route, $scope, $http, $timeout) {
					
					var dateNow = Date.now();
					
					var allDates = [];
					
					var factor = -3;
					for(var i = 0; i < 20; i++) {
						var thatDate = dateNow + factor * 1000 * 60 * 60 * 24;
						var date = new Date(thatDate);
						var thatDateFormatted = date.getFullYear() + '-' + (date.getMonth()+1) + "-" + date.getDate();
						allDates.push(thatDateFormatted);
						console.log(thatDateFormatted);
						factor += 1;
					}
					
					$scope.allDates = allDates;

					$scope.allOptionsToShow = ["Power Production", "Battery Capacity", "Energy Price (Sell)", "Energy Sold", "Energy Bought", "Clouds"];
					
				
					$scope.chartClick = function(event) {
						if ($scope.chart) {
							// Different methods depending on chart type
							// console.log( $scope.chart.getPointsAtEvent( event
							// ) ); // for Points
							// console.log( $scope.chart.getSegmentsAtEvent(
							// event ) ); // for Segments
						}
					};
					
					// Chart.js Options
					$scope.options = {
						scaleBeginAtZero : true
				        
					};
										
					$scope.drawDiagram = function() { 
						if($scope.SelectedDate == null) {
							return;
						}
						if($scope.WhatToShow == null) {
							return;
						}
						$http({
						    method: "GET",
						    url: "/getStatistics2",
						    params: {
						        day : $scope.SelectedDate
						    }
						}).success(function(result) {
//							$scope.tasks = result;
							console.log(result);
							$scope.dataForEachDay = [];
							$scope.labelsForDays = [];
							for(var i=0; i < result.length; i++) {
								var date = new Date(result[i].timestamp);
								// Hours part from the timestamp
								var hours = date.getHours();
								// Minutes part from the timestamp
								var minutes = "0" + date.getMinutes();
								// Seconds part from the timestamp
								var seconds = "0" + date.getSeconds();

								// Will display time in 10:30:23 format
								var formattedTime = hours + ':' + minutes.substr(-2);
								if($scope.WhatToShow == "Power Production") {
									$scope.dataForEachDay.push(result[i].energyProduction); 
								} else if ($scope.WhatToShow == "Battery Capacity") {
									$scope.dataForEachDay.push(result[i].batteryCurrentCapacity);
								} else if($scope.WhatToShow == "Energy Price (Sell)") {
									$scope.dataForEachDay.push(result[i].energyPriceSell);
								} else if($scope.WhatToShow == "Energy Sold") {
									if(result[i].buySellMap != null && result[i].buySellMap.SOLD != null) {
										$scope.dataForEachDay.push(result[i].buySellMap.SOLD);
									} else {
										$scope.dataForEachDay.push(0);
									}
								} else if ($scope.WhatToShow == "Energy Bought") {
									if(result[i].buySellMap != null && result[i].buySellMap.BOUGHT != null) {
										$scope.dataForEachDay.push(result[i].buySellMap.BOUGHT);
									} else {
										$scope.dataForEachDay.push(0);
									}
								} else if($scope.WhatToShow == "Clouds") {
									$scope.dataForEachDay.push(result[i].clouds);
								}
								$scope.labelsForDays.push(formattedTime);
							}
							
							console.log($scope.dataForEachDay);
							console.log($scope.labelsForDays);
							
							// Chart.js Data
							$scope.data = {
								labels : $scope.labelsForDays,
								datasets : [ {
									label : $scope.SelectedDate,
									fillColor : 'rgba(220,220,220,0.2)',
									strokeColor : 'rgba(220,220,220,1)',
									pointColor : 'rgba(220,220,220,1)',
									pointStrokeColor : '#fff',
									pointHighlightFill : '#fff',
									pointHighlightStroke : 'rgba(220,220,220,1)',
									data : $scope.dataForEachDay
								} ]
							};
						}).error(function(result) {
							console.log('ERROR in setuping function!');
						});

						$scope.labelsForDays = [0 ];

						$scope.dataForEachDay = [0];

						// Chart.js Data
						$scope.data = {
							labels : $scope.labelsForDays,
							datasets : [ {
								label : $scope.SelectedDate,
								fillColor : 'rgba(220,220,220,0.2)',
								strokeColor : 'rgba(220,220,220,1)',
								pointColor : 'rgba(220,220,220,1)',
								pointStrokeColor : '#fff',
								pointHighlightFill : '#fff',
								pointHighlightStroke : 'rgba(220,220,220,1)',
								data : $scope.dataForEachDay
							} ]
						};

						
					};
					
					$scope.$watch('WhatToShow', function() { $scope.drawDiagram(); }, true);

					$scope.$watch('SelectedDate', function() { $scope.drawDiagram(); }, true);
					

				});
