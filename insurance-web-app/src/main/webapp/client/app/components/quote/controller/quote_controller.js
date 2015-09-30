angular.module('quoteController', ['quoteService'])
.controller('QuoteEntryController',
    ['$scope', '$http', '$location', 'QuoteWrapper', 'AnotherWrapper',
    function ($scope, $http, $location, QuoteWrapper, AnotherWrapper){

        console.log("Inside QuoteEntryController");

        function initScopeVars ($scope){

            console.log("Inside initScopeVars");

            $scope.QuoteStatus  = {
                "FORM_INCOMPLETE": "FORM_INCOMPLETE",
                "NEED_ELIGIBILITY_CHECK": "NEED_ELIGIBILITY_CHECK",
                "ELIBIBILITY_COMPLETE": "ELIBIBILITY_COMPLETE",
                "QUOTE_VALID_WITH_PRICE": "QUOTE_VALID_WITH_PRICE"

            };

            $scope.IndividualEvents = {

                "updateQuoteStatus": function ($scope){
                    console.log("Inside updateQuoteStatus");
                    if ($scope.mainForm.$valid) {
                        $scope.quoteStatus = $scope.QuoteStatus.NEED_ELIGIBILITY_CHECK;
                    }
                    else {

                        $scope.quoteStatus = $scope.QuoteStatus.FORM_INCOMPLETE;
                    }
                },
                "childCareBusinessExists": function ($scope){
                    if (false == $scope.newProperty.childCareBusinessExists) {
                        $scope.newProperty.childCareLiabilityCoverageRequired = "false";
                        $scope.newProperty.childCareLiabilityAlreadyExists = "false";

                    }

                },
                "childCareLiabilityCoverageRequired": function ($scope){
                    if (false == $scope.newProperty.childCareLiabilityCoverageRequired) {

                        $scope.newProperty.childCareLiabilityAlreadyExists = "false";

                    }
                },
                "childCareLiabilityAlreadyExists": function ($scope){

                },
                "previousClaims": function ($scope){
                    $scope.showDeleteClaimAlert = false;
                    if ($scope.newProperty.previousClaims == "true") {
                        $scope.quoteStatus = $scope.QuoteStatus.FORM_INCOMPLETE;

                    }
                },
                "dogExists": function ($scope){
                    $scope.showDogDeleteAlert = false;
                    if ($scope.newProperty.dogExists == "true") {
                        $scope.quoteStatus = $scope.QuoteStatus.FORM_INCOMPLETE;
                    }
                },
                "handleEvent": function ($scope){
                    console.log("Inside IndividualEvents handleEvent");
                    try {
                        var source = window.event.srcElement.id;
                        console.log('source id ', source);
                        if (source != undefined && source.isEmpty()) {
                            source = window.event.srcElement.name;
                            console.log('source name  ', source);
                        }

                        if (source == undefined || source.isEmpty()) {
                            source = $scope.currentSource;
                            console.log('resorting to curent source field');
                        }

                        var func = this[source];

                        if (func) {
                            func($scope);
                        }
                    } catch (err) {
                        console.error("Inside IndividualEvents handleEvent", err);
                    }

                }

            };

            //$scope.QuoteStatus = QuoteStatus;
            $scope.wrapper = {};
            $scope.newApplicant = {};
            $scope.qmap = {};
            $scope.templates = 'partials/applicant.html';
            // $scope.propertyMap = {};
            $scope.newProperty = {};
            $scope.templatesApplicant = 'client/app/components/old/applicant.html';
            $scope.templatesProperty = 'client/app/components/old/property.html';
            $scope.quoteStatus = $scope.QuoteStatus.FORM_INCOMPLETE;
            // html input elements that fired
            $scope.currentSource = undefined;
            $scope.showDeleteClaimAlert = false;
            $scope.showDogDeleteAlert = false;
            $scope.errorQuoteMessages = [];
            $scope.warningQuoteMessages = [];
            $scope.infoQuoteMessages = [];

            $scope.PolicyBeginDatePickerCtrl = function ($scope, $timeout) {

                $scope.today = function(newProperty) {

                    console.log("Inside today: ", newProperty);
                    newProperty.policyBeginDate = new Date();

                };

                //$scope.today();   // set the date to default to today's date

                $scope.clear = function (newProperty) {
                    newProperty.policyBeginDate = null;
                };

                // Disable weekend selection
                $scope.disabled = function(date, mode) {
                    return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
                };

                $scope.open = function() {
                    $timeout(function() {
                        $scope.opened = true;
                    });
                };

                $scope.dateOptions = {
                    'year-format': "'yy'",
                    'starting-day': 1
                };
            };
        }

        initScopeVars($scope);

        function doQuoteMessages ($scope){

            var messages = $scope.wrapper.quoteMessages;
            $scope.errorQuoteMessages = [];
            $scope.infoQuoteMessages = [];
            $scope.warningQuoteMessages = [];
            for (var i = 0; i < messages.length; i++) {
                var msg = messages[i];
                if ("ERROR" == msg.messageStatus) {
                    $scope.errorQuoteMessages.push(msg);
                }
                if ("INFO" == msg.messageStatus) {
                    $scope.infoQuoteMessages.push(msg);
                }
                if ("WARNING" == msg.messageStatus) {
                    $scope.warningQuoteMessages.push(msg);
                }

            }

        }

        // convert a string to a Date object. Angular 1.3+ require a true Date object when using input type date or time
        // The strDate can be in the format of "yyyy-MM-dd" or just a number: 1443585600000
        function convertToDate (strDate){

            console.log("Inside convertToDate: ", strDate);


            if (!strDate || strDate.length == 0) {
                return;
            }
            return new Date(strDate);   // format: yyyy-MM-dd


        }

        function copyQuoteDataToScope ($scope, data){

            console.log('inside copyQuoteDataToScope', data.applicantQuestMap);
            $scope.wrapper = data;
            doQuoteMessages($scope);
            $scope.newApplicant = data.applicant;
            $scope.newProperty = data.property;
            $scope.qmap = data.applicantQuestMap;
            // $scope.propertyMap = data.propertyQuestMap;
            console.log('newApplicant: ', $scope.newApplicant);
            console.log('qMap: ', $scope.qMap);
            console.log('newProperty: ', $scope.newProperty);

            Try.these(function (){

                console.log("try to convert policyBeginDate: ", $scope.newProperty.policyBeginDate);

                $scope.newProperty.policyBeginDate = convertToDate($scope.newProperty.policyBeginDate); // input format is yyyy-MM-dd
            });
            Try.these(function (){

                console.log("try to convert purchaseDate: ", $scope.newProperty.purchaseDate);

                $scope.newProperty.purchaseDate = convertToDate($scope.newProperty.purchaseDate);
            });

            //converting to correct date format
            try {
                for (var idx = 0; idx < $scope.newProperty.claims.length; idx++) {
                    var cl = $scope.newProperty.claims[idx];

                    try {
                        cl.claimDate = convertToDate(cl.claimDate);
                    } catch (err) {
                        console.error(err);
                    }

                }
            } catch (err) {
                console.log("error date ", err);
            }

            var f = function (map){
                angular.forEach(map, function (val, key){
                    //	var it = iter;
                    //console.log('key,value',key+","+val);

                    if ("0" == val || 0 == val) {
                        console.log("set field["+key+"] to null");
                        map[key] = undefined;

                    }

                    if (val != undefined) {
                        var str = val.toString();
                        if ("true" == str || "false" == str) {
                            map[key] = val.toString();
                        }

                    }
                    //else{
                    //    console.log("field["+key+"] is null")
                    //    map[key] = "";  //default
                    //
                    //}

                });

            };

            f($scope.newProperty);

            f($scope.newApplicant);

        }

            $scope.stateList = AnotherWrapper.getStates();

            QuoteWrapper.query(function (data){
                console.log("after QuoteWrapper.query, data: ", data);

                copyQuoteDataToScope($scope, data);
                $scope.addClaimRow();

            });

            $scope.hideField = function (){
                console.log("hideField");
                $scope.newApplicant.filedForBankruptcy = false;
                $scope.qmap.filedForBankruptcy.enabled = false;
                console.log("$scope.qmap.filedForBankruptcy.enabled  : " + $scope.qmap.filedForBankruptcy.enabled);

            };
            $scope.changeHandle = function (serverCall){
                console.log('Inside changeHandle');

                $scope.quoteStatus = $scope.QuoteStatus.FORM_INCOMPLETE;

                Try.these(function (){
                    $scope.currentSource = window.event.srcElement.id;
                    $scope.currentSource = ($scope.currentSource == undefined || $scope.currentSource.isEmpty() ) ? window.event.srcElement.name : $scope.currentSource;
                });

                //empty value not calling server
                /*try{
                 if(window.event.srcElement != undefined){
                 var val = window.event.srcElement.value;
                 var strVal = val.toString();
                 if(strVal.isEmpty()){
                 console.log('change event not happening empty value');
                 return;
                 }
                 }
                 }catch(err){

                 }*/

                console.log("field name : " + $scope.currentSource);

                //some change don't require server call
                if ('noserver-call' === serverCall) {
                    $scope.IndividualEvents.updateQuoteStatus($scope);
                    $scope.currentSource = undefined;
                    return;
                }

                console.log("sending data to rule engine : ", $scope.wrapper);

                QuoteWrapper.save($scope.wrapper, function (data){

                        console.log("Done saving in QuoteWrapper.save");

                        copyQuoteDataToScope($scope, data);

                        console.log('newApplicant', $scope.newApplicant);
                        console.log('newProperty', $scope.newProperty);
                        console.log('qmap', $scope.qmap);

                        $scope.wrapper.property.status = "";
                        $scope.wrapper.quote = {};

                        console.log("add dummy claim and dummy dog");
                        $scope.addClaimRow();
                        $scope.addDogs();

                        $scope.IndividualEvents.updateQuoteStatus($scope);
                        $scope.IndividualEvents.handleEvent($scope);

                        $scope.currentSource = undefined;
                    },
                    function (err){
                        console.error('save:received an error: ', err);
                        if ((err.status == 409) || (err.status == 400)) {
                            $scope.errors = err.data;
                        }
                        else {
                            $scope.errorMessages = ['Unknown  server error'];
                        }
                    });

            };

            $scope.resetQuote = function (){
                $scope.mainForm.$setPristine();
                $scope.newApplicant = {};
                $scope.newProperty = {};
                $scope.IndividualEvents.updateQuoteStatus($scope);

            };

            $scope.goToPropery = function (){

                // $location.path('/property');
                $scope.templates = 'partials/property.html';
            };
            $scope.goToApplication = function (){

                // $location.path('/applicant');
                $scope.templates = 'partials/applicant.html';
            };

            $scope.getEligibility = function (){
                console.log("Inside getEligibility");

                $scope.wrapper.property.status = "";
                QuoteWrapper
                    .checkEligibility($scope.wrapper,
                    function (data){
                        copyQuoteDataToScope($scope, data);
                        $scope.newProperty.status = $scope.newProperty.status == undefined ? "" : $scope.newProperty.status;
                        if ($scope.mainForm.$valid && $scope.newProperty.status.isEmpty()) {
                            $scope.quoteStatus = $scope.QuoteStatus.ELIBIBILITY_COMPLETE;
                        }
                    },
                    function (err){
                        console.error('checkEligibility:received an error: ', err);
                        if ((err.status == 409)
                            || (err.status == 400)) {
                            $scope.errors = err.data;
                        }
                        else {
                            $scope.errorMessages = ['Unknown  server error'];
                        }
                    });
            };

            // questRes
            // quoteCalculate
            $scope.quoteCalculate = function (){
                QuoteWrapper
                    .quoteCalculate(
                    $scope.wrapper,
                    function (data){
                        copyQuoteDataToScope($scope, data);
                        console.log("wrapper : ", $scope.wrapper.quoteMessages);
                    },
                    function (result){
                        if ((result.status == 409)
                            || (result.status == 400)) {
                            $scope.errors = result.data;
                        }
                        else {
                            $scope.errorMessages = ['Unknown  server error'];
                        }
                    });

            };
            // qmap['p.previousClaims'].enabled == true
            $scope.addClaimRow = function (source){

                console.log('$scope.newProperty.claims', $scope.newProperty.claims);

                var claimRowRequired = $scope.newProperty.previousClaims == "true"
                    && $scope.qmap['p.claimDate'].enabled === true
                    && $scope.qmap['p.claimAmount'].enabled === true;

                if (!claimRowRequired) {
                    $scope.newProperty.claims = [];
                    return;
                }

                //first time  no claims initial claim
                if ($scope.newProperty.claims == undefined || $scope.newProperty.claims.length == 0) {
                    $scope.newProperty.claims = [];

                    $scope.newProperty.claims.push({
                        "claimDate": null,
                        "claimAmount": null
                    });
                }
                else {

                    //add caim button
                    if ('add' == source) {
                        //var claim = $scope.newProperty.claims[$scope.newProperty.claims.length - 1];
                        $scope.newProperty.claims.push({
                            "claimDate": "",
                            "claimAmount": ""
                        });
                        $scope.quoteStatus = $scope.QuoteStatus.FORM_INCOMPLETE;
                    }

                }

                //converting to correct date format
                for (var idx = 0; idx < $scope.newProperty.claims.length; idx++) {
                    var cl = $scope.newProperty.claims[idx];
                    try {
                        cl.claimDate = convertToDate(cl.claimDate);
                    } catch (err) {
                        console.error(err);
                    }

                }

            };

            $scope.deleteClaimRow = function ($index){

                console.log('inside : deleteClaimRow');

                if ($scope.newProperty.claims.length > 1) {
                    $scope.newProperty.claims.splice($index, 1);
                }
                else {
                    $scope.showDeleteClaimAlert = true;
                }
                console.log('showDeleteClaimAlert : ' + $scope.showDeleteClaimAlert);

            };

            $scope.hideClaimAlert = function (){
                $scope.showDeleteClaimAlert = false;

            };

            $scope.dogList = [];

            $scope.addDogs = function (source){

                console.log('Inside addDogs');
                console.log('$scope.currentSource : ', $scope.currentSource);
                console.log('source: ', source);
                if (false == ('dogExists' == $scope.currentSource || 'add' == source )) {
                    return;
                }
                if ($scope.qmap['p.dogs'].enabled === false) {
                    $scope.dogList = [];
                    return;

                }

                $scope.dogList.push({
                    "dogCount": undefined,
                    "dogType": ""
                });

            };

            $scope.deleteDogs = function (){

                $scope.dogList = [];

            };

            $scope.removeDog = function ($index){
                $scope.showDogDeleteAlert = false;
                if ($scope.dogList.length == 1) {
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

        }]);
