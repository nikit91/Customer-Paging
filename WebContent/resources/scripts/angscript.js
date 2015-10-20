var app = angular.module('myApp', [ 'ngTouch', 'ui.grid', 'ui.grid.exporter' ]);

app.controller('customersCtrl', [
		'$scope',
		'$http',
		'$interval',
		'$q',
		function($scope, $http, $interval, $q) {

			$scope.pNo = 1;
			$scope.pSize = 3;
			$scope.pageCount = 0;
			$scope.nextPageDisFlag = true;
			$scope.prevPageDisFlag = true;

			var fakeI18n = function(title) {
				var deferred = $q.defer();
				$interval(function() {
					deferred.resolve('col: ' + title);
				}, 1000, 1);
				return deferred.promise;
			};

			$scope.gridOptions = {
				exporterMenuCsv : false,
				enableGridMenu : true,
				gridMenuTitleFilter : fakeI18n,
				columnDefs : [ {
					name : 'Customer Id',
					field : 'customerid'
				}, {
					name : 'Customer Name',
					field : 'customername'
				}, {
					name : 'Customer Email',
					field : 'customeremail'
				} ],

			};
			var custCallback = function(data, arg) {
				var pageNumber = arg.pageNumber;
				var pageSize = arg.pageSize;
				$scope.$apply(function() {

					if (pageNumber < $scope.pageCount)
						$scope.nextPageDisFlag = false;
					else
						$scope.nextPageDisFlag = true;
					if (pageNumber > 1)
						$scope.prevPageDisFlag = false;
					else
						$scope.prevPageDisFlag = true;
					$scope.gridOptions.data = data
				});

			};
			var getPagedData = function(pageNumber, pageSize) {
				var to = pageSize * pageNumber;
				var from = to - pageSize;
				var callMetaData = {
					callback : custCallback,
					arg : {
						pageNumber : pageNumber,
						pageSize : pageSize
					}
				};
				RequestReceiver.getPagedCustomerData(from, pageSize,
						callMetaData);
			};
			var setPageCountCB = function(data) {
				$scope.pageCount = Math.ceil(data/$scope.pSize);
			};
			var setPageCount = function() {
				var callMetaData = {
						async: false,
						callback: setPageCountCB
				}
				RequestReceiver.getCustCount(callMetaData);
			}
			$scope.updateTable = function(pSize) {
				if (pSize > 0) {
					$scope.pSize = pSize;
					$scope.pNo = 1;
					setPageCount();
					getPagedData($scope.pNo, $scope.pSize);
				}
			};
			$scope.updateTable($scope.pSize);

			$scope.showNext = function() {
				$scope.pNo += 1;
				getPagedData($scope.pNo, $scope.pSize);
			};
			$scope.showPrev = function() {
				$scope.pNo -= 1;
				getPagedData($scope.pNo, $scope.pSize);
			};
			$scope.gotoPage = function(pNo) {
				if (pNo > 0 && pNo <= $scope.pageCount) {
					getPagedData(pNo, $scope.pSize);
				}
			};
		} ]);