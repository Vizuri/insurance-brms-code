var module = angular.module('quoteService', [ 'ngResource' ]);



module.factory('QuoteWrapper', function($resource) {
	
	//return $resource("http://localhost:8080/insurance-web-app/rest/quoteService",{});



	//during development using http server as front server for faster develpment speed
	//doing this requires a actual rest server host, which is the jboss eap http location to be added
	var resourceUri = "rest/quoteService/:controller";
	if ( window.location.origin.match(/http\:\/\/insurance:8000/) ){
		resourceUri = "http://localhost:8080/insurance-web-app/"+resourceUri;
	}
	var questRes = $resource(resourceUri,
	{
		controller : "@controller"
	}, {
		query : {
			method : "GET",
			isArray : false
		},
		save : {
			
			method : "POST",
			isArray : false
		},
		checkEligibility :{
			method : "POST",
			isArray : false,
			params : {controller : "eligibility"}
			
			
		},
		quoteCalculate :{
			method : "POST",
			isArray : false,
			params : {controller : "quoteCalculate"}
			
			
		}
	});

	
	return questRes;
	
});





