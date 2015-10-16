
// Define any routes for the app
// Note that this app is a single page app, and each partial is routed to using the URL fragment.
angular.module('insurance-demo', ['ngRoute','quoteController','quoteService', 'ui.bootstrap', 'ui.mask'])
    .config([ '$httpProvider', '$routeProvider', function($httpProvider, $routeProvider) {

            $routeProvider.
            when('/applicant',{
                templateUrl : 'client/app/components/old/applicant.html',
                controller : 'ApplicantEntryController'
            })
            .when('/property',{
                templateUrl : 'partials/property.html',
                controller : 'PropertyEntryController'
            })
            .when('/quote',{
            	templateUrl : 'client/app/components/quote/view/quote_view.html',
            	controller : 'QuoteEntryController'
            })
            .otherwise({
                redirectTo : '/quote'
            });
    }]);