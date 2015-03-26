var quoteModdule = angular.module('quoteController', [ 'quoteService' ]);

QuoteStatus = {
	"FORM_INCOMPLETE" : "FORM_INCOMPLETE",
	"NEED_ELIGIBILITY_CHECK" : "NEED_ELIGIBILITY_CHECK",
	"ELIBIBILITY_COMPLETE" : "ELIBIBILITY_COMPLETE",
	"QUOTE_VALID_WITH_PRICE" : "QUOTE_VALID_WITH_PRICE"

};
IndividualEvents = {

	"updateQuoteStatus" : function ($scope){
		if ($scope.mainForm.$valid) {
			$scope.quoteStatus = QuoteStatus.NEED_ELIGIBILITY_CHECK;
		} else {

			$scope.quoteStatus = QuoteStatus.FORM_INCOMPLETE;
		}
	},
	"childCareBusinessExists" : function($scope){
		if ( false == $scope.newProperty.childCareBusinessExists ){
			$scope.newProperty.childCareLiabilityCoverageRequired = false;
			$scope.newProperty.childCareLiabilityAlreadyExists = false;

		}

	},
	"childCareLiabilityCoverageRequired" : function($scope){
		if ( false == $scope.newProperty.childCareLiabilityCoverageRequired ){
			
			$scope.newProperty.childCareLiabilityAlreadyExists = false;

		}
	},
	"childCareLiabilityAlreadyExists" :function($scope){

	},
	"previousClaims" : function($scope){
		$scope.showDeleteClaimAlert = false;
		if($scope.newProperty.previousClaims == true ) {
			$scope.quoteStatus = QuoteStatus.FORM_INCOMPLETE;

		}
	},
	"dogExists" : function ($scope){
			$scope.showDogDeleteAlert = false;
			if($scope.newProperty.dogExists == true ) {
				$scope.quoteStatus = QuoteStatus.FORM_INCOMPLETE;
			}
	},
	"handleEvent" : function($scope){
		console.log("Inside IndividualEvents handleEvent");
		try{
			var source = window.event.srcElement.id;
			console.log('source id ',source);
			if(source != undefined && source.isEmpty()){
				source = window.event.srcElement.name;
				console.log('source name  ',source);
			}

			if(source == undefined || source.isEmpty() ){
				source = $scope.currentSource;
				console.log('resorting to curent source field');
			}

			var func = this[source];

			if(func){
				func($scope);
			}
		}catch(err){
			console.error("Inside IndividualEvents handleEvent",err);
		}

	}


};

function initScopeVars($scope) {

	$scope.QuoteStatus = QuoteStatus;
	$scope.wrapper = {};
	$scope.newApplicant = {};
	$scope.qmap = {};
	$scope.templates = 'partials/applicant.html';
	// $scope.propertyMap = {};
	$scope.newProperty = {};
	$scope.templatesApplicant = 'partials/applicant.html';
	$scope.templatesProperty = 'partials/property.html';
	$scope.quoteStatus = QuoteStatus.FORM_INCOMPLETE;
	// html input elements that fired
	$scope.currentSource = undefined;
	$scope.showDeleteClaimAlert = false;
	$scope.showDogDeleteAlert = false;
}

function convertToDate(obj){
			var dt = obj;
			if(dt == undefined){
				return;
			}
			var date = new Date(dt);
			return date;
	
}


function copyQuoteDataToScope($scope, data) {
	console.log('inside copyQuoteDataToScope');
	$scope.wrapper = data;
	$scope.newApplicant = data.applicant;
	$scope.newProperty = data.property;
	$scope.qmap = data.applicantQuestMap;
	// $scope.propertyMap = data.propertyQuestMap;
	console.log('newApplicant', $scope.newApplicant);
	console.log('qMap', $scope.qMap);
	console.log('newProperty', $scope.newProperty);

	
	Try.these( function (){
			$scope.newProperty.policyBeginDate =convertToDate($scope.newProperty.policyBeginDate);
		} );
	Try.these( function (){
		$scope.newProperty.purchaseDate =convertToDate($scope.newProperty.purchaseDate);
	} );
	
	angular.forEach($scope.newProperty, function (val,key){
	//	var it = iter;
		//console.log('key,value',key+","+val);
		
		if("0" == val || 0 == val){
			$scope.newProperty[key] = undefined;
			
		}
		
		//console.log('key,value',key+","+$scope.newProperty[key]);
		
		
	});

}

quoteModdule.controller('QuoteEntryController',
				[		'$scope',
						'$http',
						'$location',
						'QuoteWrapper',
						'$filter',
						function($scope, $http, $location, QuoteWrapper,$filter) {

							initScopeVars($scope);
								
							QuoteWrapper.query(function(data) {
								console.log("data : ", data);

								copyQuoteDataToScope($scope, data);
								$scope.addClaimRow();

							});

							$scope.hideField = function() {
								console.log("hideField");
								$scope.newApplicant.filedForBankruptcy = false;
								$scope.qmap.filedForBankruptcy.enabled = false;
								console	.log("$scope.qmap.filedForBankruptcy.enabled  : "+ $scope.qmap.filedForBankruptcy.enabled);

							};
							$scope.changeHandle = function(serverCall) {
								console.log('hello');
								
								$scope.quoteStatus = QuoteStatus.FORM_INCOMPLETE;
								
								Try.these( function (){
										$scope.currentSource = window.event.srcElement.id;
										$scope.currentSource = ($scope.currentSource == undefined || $scope.currentSource.isEmpty() )? window.event.srcElement.name : $scope.currentSource;
								});
								
								//empty value not calling server
								try{
									if(window.event.srcElement != undefined){
										var val = window.event.srcElement.value;
										var strVal = val.toString();
										if(strVal.isEmpty()){
											console.log('change event not happening empty value');
											return;
										}
									}
								}catch(err){
									
								}
								
								
								console.log("event source : "+$scope.currentSource);
								
								
								//some change don't require server call
								if('noserver-call' === serverCall){
									IndividualEvents.updateQuoteStatus($scope);
									$scope.currentSource = undefined;									
									return;
								}

								QuoteWrapper.save($scope.wrapper,function(data) {
													copyQuoteDataToScope($scope, data);

													console.log('newApplicant',	$scope.newApplicant);
													console.log('newProperty',	$scope.newProperty);
													console.log('qmap',	$scope.qmap);

													$scope.wrapper.property.status = "";
													$scope.wrapper.quote = {};

													$scope.addClaimRow();
													$scope.addDogs();

													IndividualEvents.updateQuoteStatus($scope);	
													IndividualEvents.handleEvent($scope);
													
													$scope.currentSource = undefined;												
												},
												function(result) {
													if ((result.status == 409)|| (result.status == 400)) {
														$scope.errors = result.data;
													} else {
														$scope.errorMessages = [ 'Unknown  server error' ];
													}
												});
								console.log('in change');
							};

							$scope.goToPropery = function() {

								// $location.path('/property');
								$scope.templates = 'partials/property.html';
							};
							$scope.goToApplication = function() {

								// $location.path('/applicant');
								$scope.templates = 'partials/applicant.html';
							};

							$scope.getEligibility = function() {
								$scope.wrapper.property.status = "";
								QuoteWrapper
										.checkEligibility(
												$scope.wrapper,
												function(data) {
													copyQuoteDataToScope($scope, data);
													$scope.newProperty.status = $scope.newProperty.status == undefined ? "" : $scope.newProperty.status;
													if ($scope.mainForm.$valid && $scope.newProperty.status.isEmpty()) {
														$scope.quoteStatus = QuoteStatus.ELIBIBILITY_COMPLETE;
													}
												},
												function(result) {
													if ((result.status == 409)
															|| (result.status == 400)) {
														$scope.errors = result.data;
													} else {
														$scope.errorMessages = [ 'Unknown  server error' ];
													}
												});
							};

							// questRes
							// quoteCalculate
							$scope.quoteCalculate = function() {
								QuoteWrapper
										.quoteCalculate(
												$scope.wrapper,
												function(data) {
													copyQuoteDataToScope($scope, data);
													
												},
												function(result) {
													if ((result.status == 409)
															|| (result.status == 400)) {
														$scope.errors = result.data;
													} else {
														$scope.errorMessages = [ 'Unknown  server error' ];
													}
												});

							};
							// qmap['p.previousClaims'].enabled == true
							$scope.addClaimRow = function(source) {
								
								console.log('$scope.newProperty.claims',	$scope.newProperty.claims);

								var claimRowRequired = $scope.newProperty.previousClaims === true
										&& $scope.qmap['p.claimDate'].enabled === true
										&& $scope.qmap['p.claimAmount'].enabled === true;
								
								if (!claimRowRequired) {
									$scope.newProperty.claims = [];
									return;
								}

								//first time  no claims initial claim
								if ($scope.newProperty.claims == undefined	|| $scope.newProperty.claims.length == 0) {
									$scope.newProperty.claims = [];

									$scope.newProperty.claims.push({
										"claimDate" : null,
										"claimAmount" : null
									});
								} else {
									
									//add caim button
									if('add' == source){
										//var claim = $scope.newProperty.claims[$scope.newProperty.claims.length - 1];
										$scope.newProperty.claims.push({
											"claimDate" : "",
											"claimAmount" : ""
										});
										$scope.quoteStatus = QuoteStatus.FORM_INCOMPLETE;
									}


								}
								
								//converting to correct date format
								for(var idx = 0 ;idx < $scope.newProperty.claims.length; idx++){
									var cl = $scope.newProperty.claims[idx];
									try{
										cl.claimDate = convertToDate(cl.claimDate);
									}catch(err){
										console.error(err);
									}
									
									
								}

							};

							$scope.deleteClaimRow = function($index) {

								console.log('inside : deleteClaimRow');
									
								if ($scope.newProperty.claims.length > 1) {
									$scope.newProperty.claims.splice($index, 1);
								}else{
									$scope.showDeleteClaimAlert = true;
								}
								console.log('showDeleteClaimAlert : '+ $scope.showDeleteClaimAlert) ;

 
							};

							$scope.hideClaimAlert = function (){
									$scope.showDeleteClaimAlert = false;


							};

							$scope.dogList = [];
							$scope.addDogs = function(source) {
								console.log('in add dogs ');
								console.log('$scope.currentSource : ',$scope.currentSource);
								console.log('source: ',source);
								if( false == ('dogExists' == $scope.currentSource  || 'add' == source )){
									return;
								}
								if ($scope.qmap['p.dogs'].enabled === false) {
									$scope.dogList = [];
									return;

								}

								$scope.dogList.push({
									"dogCount" : undefined,
									"dogType" : ""
								});

							};

							$scope.deleteDogs = function() {

								$scope.dogList = [];

							};
							
							$scope.removeDog = function($index) {
								$scope.showDogDeleteAlert = false;
								if($scope.dogList .length == 1){
									$scope.showDogDeleteAlert = true;
									return;
								}

								if ($scope.dogList.length > 0) {
									$scope.dogList.splice($index, 1);
								}
							};
							$scope.hideDogAlert = function (){
									$scope.showDogDeleteAlert = false;


							};


						} ]);
