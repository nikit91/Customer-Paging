var app = angular.module('myApp', [ 'ngTouch', 'ui.grid', 'ui.grid.exporter' ]);

app.controller('customersCtrl', [ '$scope', '$http', '$interval', '$q',
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
			var getPagedData = function(pageNumber, pageSize) {
				var data = [ {
					customerid : '1',
					customername : "Nikit",
					customeremail : "nikit@tcs.com"
				}, {
					customerid : '2',
					customername : "Gurpreet",
					customeremail : "gurpreet@tcs.com"
				}, {
					customerid : '3',
					customername : "Varun",
					customeremail : "varun@tcs.com"
				}, {
					customerid : '4',
					customername : "Vilas",
					customeremail : "vilas@tcs.com"
				}, {
					customerid : '5',
					customername : "Jagruti",
					customeremail : "jagruti@tcs.com"
				}, {
					customerid : '6',
					customername : "Era",
					customeremail : "era@tcs.com"
				}, {
					customerid : '7',
					customername : "Karan",
					customeremail : "karan@tcs.com"
				} ];
				var to = pageSize * pageNumber;
				var from = to - pageSize;
				if (data.length > to)
					$scope.nextPageDisFlag = false;
				else
					$scope.nextPageDisFlag = true;
				if (from > 0)
					$scope.prevPageDisFlag = false;
				else
					$scope.prevPageDisFlag = true;
				$scope.gridOptions.data = data.slice(from, to);
				$scope.pageCount = Math.ceil(data.length / pageSize);
			};
			/*
			 * call2 = function(data){ alert("recieved");
			 * $scope.$apply(function() { $scope.names = data; }); };
			 * RequestReceiver.getCustomers(call2);
			 */
			$scope.updateTable = function(pSize) {
				if (pSize > 0) {
					$scope.pSize = pSize;
					$scope.pNo = 1;
					getPagedData($scope.pNo, $scope.pSize);
				}
			};
			getPagedData($scope.pNo, $scope.pSize);

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
					getPagedData($scope.pNo, $scope.pSize);
				}
			};
		} ]);