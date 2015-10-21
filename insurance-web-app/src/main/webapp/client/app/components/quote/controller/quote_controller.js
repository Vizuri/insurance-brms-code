angular.module('quoteController', ['quoteService'])
.controller('QuoteEntryController',
    function ($scope, $http, $location, QuoteInit, QuoteUpdate, QuoteCalculate, GUIConstants){

        console.log("Inside QuoteEntryController");

        function initScopeVars ($scope){

            console.log("Inside initScopeVars");

            $scope.yesNoOptions = [
                {value: null, label: 'Select One'},
                {value: 'true', name: 'Yes'},
                {value: 'false', name: 'No'}
            ];

            $scope.newDog = {
                "count": 1,
                "type": ""
            };

            $scope.newClaim = {
                "claimDate": new Date(),
                "claimAmount": 100
            };

            $scope.QuoteStatus  = {
                "FORM_INCOMPLETE": "FORM_INCOMPLETE",
                "NEED_ELIGIBILITY_CHECK": "NEED_ELIGIBILITY_CHECK",
                "ELIBIBILITY_COMPLETE": "ELIBIBILITY_COMPLETE",
                "QUOTE_VALID_WITH_PRICE": "QUOTE_VALID_WITH_PRICE"

            };

            $scope.requiredFieldList = "";
            $scope.wrapper = {};
            //$scope.newApplicant = {};
            $scope.qmap = {};
            $scope.templates = 'partials/applicant.html';
            // $scope.propertyMap = {};
            $scope.newProperty = {};
            $scope.templatesApplicant = 'client/app/components/old/applicant.html';
            $scope.templatesProperty = 'client/app/components/old/property.html';
            $scope.quoteStatus = $scope.QuoteStatus.FORM_INCOMPLETE;
            // html input elements that fired
            $scope.currentSource = undefined;
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

        function doQuoteMessages (){

            var haveErrors = false;
            var messages = $scope.wrapper.quoteMessages;
            $scope.errorQuoteMessages = [];
            $scope.infoQuoteMessages = [];
            $scope.warningQuoteMessages = [];

            if (messages && messages.length > 0){

                for (var i = 0; i < messages.length; i++) {
                    var msg = messages[i];
                    if ("ERROR" == msg.messageStatus) {
                        $scope.errorQuoteMessages.push(msg);
                        haveErrors = true;
                    }
                    if ("INFO" == msg.messageStatus) {
                        $scope.infoQuoteMessages.push(msg);
                    }
                    if ("WARNING" == msg.messageStatus) {
                        $scope.warningQuoteMessages.push(msg);
                    }

                }
            }


            return haveErrors;

        }

        // convert a string to a Date object. Angular 1.3+ require a true Date object when using input type date or time
        // The strDate can be in the format of "yyyy-MM-dd" or just a number: 1443585600000
        function convertToDate (strDate){

            console.log("Inside convertToDate: ", strDate);

            if (!strDate || strDate.length == 0) {
                return;
            }
            return new Date(strDate);   // format: yyyy-MM-dd or 123456789


        }

        function copyQuoteDataToScope (data){

            console.log('inside copyQuoteDataToScope, questionMap: ', data.questionMap);
            $scope.wrapper = data;

            // check to see if we got any error messages back
            var haveErrors = doQuoteMessages();

            if (haveErrors){
                console.log("got some errors back after validation");
                $scope.QuoteStatus.FORM_INCOMPLETE;
            }

            //$scope.newApplicant = data.applicant;
            //$scope.newProperty = data.property;
            $scope.newQuote = data.quote;

            if (data.questionMap){
                $scope.qmap = data.questionMap;
            }


            //console.log('newApplicant: ', $scope.newApplicant);
            console.log('qmap: ', $scope.qmap);
            //console.log('newProperty: ', $scope.newProperty);

            for (questionId in $scope.qmap){

                //console.log("check question: "+ questionId);

                if ($scope.qmap[questionId].answerType === "Date" && $scope.qmap[questionId].strValue != undefined){
                    console.log("convert value["+$scope.qmap[questionId].strValue+"] for questionId["+questionId+"] to date: ");
                    $scope.qmap[questionId].strValue = convertToDate($scope.qmap[questionId].strValue); // input format is yyyy-MM-dd or 12345678
                }
            }

            //if ($scope.newProperty.policyBeginDate) {
            //
            //    console.log("try to convert policyBeginDate: ", $scope.newProperty.policyBeginDate);
            //
            //    $scope.newProperty.policyBeginDate = convertToDate($scope.newProperty.policyBeginDate); // input format is yyyy-MM-dd
            //}
            //
            //if($scope.newProperty.purchaseDate){
            //
            //    console.log("try to convert purchaseDate: ", $scope.newProperty.purchaseDate);
            //
            //    // need to convert the integer number into a string representation of the date
            //    $scope.newProperty.purchaseDate = convertToDate($scope.newProperty.purchaseDate);
            //}

            //converting to correct date format
            //try {
            //    for (var idx = 0; idx < $scope.newProperty.claims.length; idx++) {
            //        var cl = $scope.newProperty.claims[idx];
            //
            //        try {
            //            cl.claimDate = convertToDate(cl.claimDate);
            //        } catch (err) {
            //            console.error(err);
            //        }
            //
            //    }
            //} catch (err) {
            //    console.log("error date ", err);
            //}

            var f = function (map){
                angular.forEach(map, function (val, key){

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

            //f($scope.newProperty);

        }

        $scope.stateList = GUIConstants.getStates();

        $scope.changeHandle = function (questionId, skipRuleEngine, cb){

            console.log("Inside changeHandle, skipRuleEngine["+skipRuleEngine+"]", questionId);


            if (questionId != undefined){

                console.log(questionId + " was changed");

                if ($scope.wrapper.answerMap == undefined){
                    $scope.wrapper.answerMap = {};
                }

                // we need to create an answer and add it to the answer list
                $scope.wrapper.answerMap[questionId] = {questionId: questionId, groupId: "" + $scope.qmap[questionId].groupId, strValue: $scope.qmap[questionId].strValue};

                if ($scope.qmap[questionId].delete == "true"){

                    console.log("delete answer for question[" + questionId + "]");
                    $scope.wrapper.answerMap[questionId].delete = "true";
                }
            }

            //some change don't require server call
            if (skipRuleEngine === true) {
                updateQuoteStatus();
                return;
            }

            console.log("sending data to rule engine : ", $scope.wrapper.answerMap);

            $scope.wrapper.questionMap = null;  // we do not send questions down only answers

            QuoteUpdate.update($scope.wrapper, function (data){

                    console.log("Done saving in QuoteWrapper.update, data: ", data);

                    copyQuoteDataToScope(data);

                    //console.log('newApplicant', $scope.newApplicant);
                    //console.log('newProperty', $scope.newProperty);
                    console.log('qmap', $scope.qmap);


                    //$scope.wrapper.quote = {};
                    //$scope.wrapper.quote.status = "";

                    updateQuoteStatus();
                    if(cb){cb();}


                },
                function (err){
                    console.error('save:received an error: ', err);
                    if (err.data) {
                        //alert("Internal error: " + err.data.message);
                        $scope.errorMessages = err.data.message;
                    }
                    else {
                        $scope.errorMessages = ['Unknown  server error'];
                    }
                    if(cb){cb();}
                });

        };

        $scope.resetQuote = function (){
            $scope.mainForm.$setPristine();

            initQuote();

            updateQuoteStatus();

        };

        $scope.quoteCalculate = function (){

            console.log("Inside quoteCalculate");

            QuoteCalculate.save($scope.wrapper,
                function (success){

                    copyQuoteDataToScope(success);
                    console.log("Calculated Quote: ", $scope.wrapper.quote);
                },
                function (error){

                    console.log("Error calling QuoteCalculate, err: ", error);
                    if ((error.status == 409) || (error.status == 400)) {
                        $scope.errors = error.data;
                    }
                    else {
                        $scope.errorMessages = ['Unknown  server error'];
                    }
                });

        };


        $scope.claimsExists = function (){

            console.log("Inside claimsExists: " + $scope.qmap['p.previousClaims'].strValue);

            if ($scope.qmap['p.previousClaims'].strValue == "false"){
                $scope.deleteAllClaims();
            }
            $scope.changeHandle('p.previousClaims');
        };

        $scope.deleteAllClaims = function (){
            $scope.newProperty.claims = undefined;
        };

        // qmap['p.previousClaims'].enabled == true
        $scope.addClaim = function (){

            console.log('Inside addClaim: ', $scope.newProperty.claims);

            if ($scope.qmap['p.previousClaims'].strValue == "false") {
                $scope.newProperty.claims = [];
                return;
            }

            //first time  no claims initial claim
            if ($scope.newProperty.claims == undefined) {
                $scope.newProperty.claims = [];
            }

            //cl.claimDate = convertToDate(cl.claimDate);
            $scope.newProperty.claims.push($scope.newClaim);

            // now add the answer for the claim
            $scope.qmap["c.claimAmount"].strValue = $scope.newClaim.claimAmount;
            $scope.qmap["c.claimAmount"].groupId = $scope.newProperty.claims.length;

            $scope.changeHandle("c.claimAmount", false, function(){
                $scope.newClaim.claimAmount = 100;
            });

        };

        $scope.removeClaim = function ($index){

            console.log('inside removeClaim');

            if ($scope.newProperty.claims.length > 0) {
                $scope.newProperty.claims.splice($index, 1);

                if ($scope.newProperty.claims.length == 0){

                    $scope.qmap['c.claimAmount'].delete = "true";
                    $scope.changeHandle("c.claimAmount", false, function(){
                        $scope.newClaim.claimAmount = 100;
                    });
                }
            }
        };

        $scope.dogsExists = function (){

            console.log("Inside dogsExists: " + $scope.newProperty.dogExists);

            if ($scope.qmap['p.dogExists'].strValue == "false"){
                $scope.deleteAllDogs();
            }
            $scope.changeHandle('p.dogExists');
        };

        $scope.deleteAllDogs = function (){
            $scope.newProperty.dogs = undefined;
        };

        $scope.addDogs = function (){

            console.log('Inside addDogs');

            if ($scope.qmap['p.dogExists'].strValue == "false") {
                console.log('did not add the dog');
                return;
            }

            if ($scope.newProperty.dogs == undefined){
                $scope.newProperty.dogs = [];
            }

            $scope.newProperty.dogs.push($scope.newDog);

            // now add the answer for the claim
            $scope.qmap["p.dogCount"].strValue = $scope.newDog.count;
            $scope.qmap["c.claimAmount"].groupId = $scope.newProperty.dogs.length;

            $scope.changeHandle("p.dogCount", false, function(){
                $scope.newDog.type = "";
            });

        };

        $scope.removeDog = function ($index){

            console.log('Inside removeDog');

            if ($scope.newProperty.dogs.length > 0) {
                $scope.newProperty.dogs.splice($index, 1);

                if ($scope.newProperty.dogs.length == 0){

                    $scope.qmap['p.dogCount'].delete = "true";
                    $scope.changeHandle("p.dogCount", false, function(){
                        $scope.newDog.type = "";
                    });
                }

            }
        };


        function updateQuoteStatus (){
            console.log("Inside updateQuoteStatus, before: " + $scope.mainForm.$valid);

            if ($scope.mainForm.$valid) {

                $scope.quoteStatus = $scope.QuoteStatus.NEED_ELIGIBILITY_CHECK;
            }
            else {

                $scope.quoteStatus = $scope.QuoteStatus.FORM_INCOMPLETE;
            }

            //console.log("status after: " + $scope.quoteStatus, $scope.mainForm.$error);

            if ($scope.mainForm.$error && $scope.mainForm.$error.required){

                $scope.requiredFieldList = "The following fields are required:\n";

                for(index in $scope.mainForm.$error.required){
                    console.log("required field: " + $scope.mainForm.$error.required[index].$name);
                    $scope.requiredFieldList += $scope.mainForm.$error.required[index].$name + "\n";
                }
            }
        }

        function initQuote(){
            console.log("Inside initQuote");

            //$scope.newApplicant = {};
            $scope.newProperty = {};

            QuoteInit.get(function (response){
                console.log("after QuoteInit.get, response: ", response);

                copyQuoteDataToScope(response);


            });
        }

        initQuote();

    });
