angular.module('myApp', [ 'ngAnimate', 'ui.bootstrap', 'ngTouch', 'ui.grid',
		'ui.grid.exporter', ]);
angular.module('myApp').controller(
		'customersCtrl',
		[
				'$scope',
				'$interval',
				'$q',
				'$timeout',
				function($scope, $interval, $q, $timeout) {
					$scope.totalItems = 0;
					$scope.itemsPerPage = 0;
					$scope.currentPage = 1;
					$scope.pSize = 0;
					$scope.showErr = false;
					var defaultPSize = 4;
					//Function to reset error panel
					var resetErrPanel = function() {
						$scope.showErr = false;
						$scope.errMsg = "";
					};
					//Function to put an error message on screen
					var putErrMsg = function(msg) {
						$scope.showErr = true;
						$scope.errMsg = msg;
					};
					//Defining grid menu structure
					var fakeI18n = function(title) {
						var deferred = $q.defer();
						$interval(function() {
							deferred.resolve('col: ' + title);
						}, 1000, 1);
						return deferred.promise;
					};
					//Defining grid structure
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

					var pageCountProxy = 12;
					var totalItemsProxy = 12;
					//callback function to setPageCount
					var setPageCountCB = function(data, arg) {
						totalItemsProxy = data;
						pageCountProxy = Math.ceil(data / arg.inPSize);
					};
					//Function to set number of records fetched and number of pages to display
					var setPageCount = function(inPSize) {
						var callMetaData = {
							async : false,
							arg : {
								scope : $scope,
								timeout : $timeout,
								inPSize : inPSize
							},
							callback : setPageCountCB
						}
						RequestReceiver.getCustCount(callMetaData);
					}
					//Callback function to record retrieval
					var custCallback = function(data, arg) {
						arg.scope.$apply(function() {
							arg.scope.gridOptions.data = data
						});

					};
					//Function to retrieve paged records from server
					var getPagedData = function(pageNumber, pageSize) {
						var to = pageSize * pageNumber;
						var from = to - pageSize;
						var callMetaData = {
							callback : custCallback,
							arg : {
								scope : $scope
							}
						};
						RequestReceiver.getPagedCustomerData(from, pageSize,
								callMetaData);
					};
					//Function to update grid whenever page size is changed
					$scope.updtGrid = function(inPSize) {
						if (inPSize > 0) {
							resetErrPanel();
							// set the page number to 1
							$scope.currentPage = 1;
							// update items per page and totalItems.
							setPageCount(inPSize);
							$scope.itemsPerPage = inPSize;
							$scope.totalItems = totalItemsProxy;
							$scope.pageCount = pageCountProxy;
							// get data for page one
							getPagedData(1, inPSize);
						} else
							putErrMsg("Invalid Page Size Entered.");
					};
					//Function to jump to a page
					$scope.gotoPage = function(inPNo) {
						if (inPNo > 0 && inPNo <= $scope.pageCount) {
							resetErrPanel();
							var pSize = $scope.itemsPerPage;
							$scope.currentPage = inPNo;
							getPagedData(inPNo, pSize);
						} else
							putErrMsg("Invalid Page Number Entered.");
					};
					//Initialize the grid
					$scope.updtGrid(defaultPSize);
					
				} ]);