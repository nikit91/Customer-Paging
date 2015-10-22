var app = angular.module('myApp', [ 'ngTouch', 'ui.grid', 'ui.grid.exporter',
		'ngAnimate', 'ui.bootstrap' ]);

app.controller('customersCtrl', [
		'$scope',
		'$http',
		'$interval',
		'$q',
		'$timeout',
		function($scope, $http, $interval, $q, $timeout) {

			$scope.pNo = 1;
			$scope.pSize = 3;
			$scope.pageCount = 0;
			$scope.totalItems = 0;
			$scope.showErr = false;

			var fakeI18n = function(title) {
				var deferred = $q.defer();
				$interval(function() {
					deferred.resolve('col: ' + title);
				}, 1000, 1);
				return deferred.promise;
			};

			var resetErrPanel = function() {
				$scope.showErr = false;
				$scope.errMsg = "";
			};
			var putErrMsg = function(msg) {
				$scope.showErr = true;
				$scope.errMsg = msg;
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
				$timeout(function() {
					$scope.totalItems = data;
					$scope.pageCount = Math.ceil(data / $scope.pSize);
				});
			};
			var setPageCount = function() {
				var callMetaData = {
					async : false,
					callback : setPageCountCB
				}
				RequestReceiver.getCustCount(callMetaData);
			}
			$scope.updateTable = function(pSize) {
				if (pSize > 0) {
					resetErrPanel();
					$scope.pSize = pSize;
					$scope.pNo = 1;
					setPageCount();
					getPagedData($scope.pNo, $scope.pSize);
				} else
					putErrMsg("Invalid Page Size Entered.");
			};
			$scope.updateTable($scope.pSize);
			$scope.gotoPage = function(pNo) {
				if (pNo > 0 && pNo <= $scope.pageCount) {
						$scope.pNo = pNo;
						resetErrPanel();
						getPagedData($scope.pNo, $scope.pSize);
				} else
					putErrMsg("Invalid Page Number Entered.");
			};
		} ]);