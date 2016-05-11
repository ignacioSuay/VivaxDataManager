(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('UploadStudyController', UploadStudyController);

    UploadStudyController.$inject = ['$scope', '$state', '$http', 'Form', '$uibModal'];
    console.log('In publication controller');

    function UploadStudyController ($scope, $state, $http, Form, $uibModal) {

        $scope.publi;
        $scope.pubMedId;
        $scope.studies = [];
        $scope.siteDatas = [];
        $scope.treatments = [];

        $scope.retrievePublicationByPubMedId = function () {
            Form.load($scope.pubMedId).then(function (result) {
                $scope.publi = result.data;
                $scope.myHidingValue=true;
                $scope.studies=$scope.publi.studies;
                for (var i=0; i<=$scope.publi.siteDatas[0].length; i++){
                    if($scope.publi.siteDatas[0][i]!=undefined) {
                        $scope.siteDatas.push($scope.publi.siteDatas[0][i]);
                    }
                }
                for (var i=0; i<=$scope.publi.treatments[0].length; i++){
                    if($scope.publi.treatments[0][i]!=undefined) {
                        $scope.treatments.push($scope.publi.treatments[0][i]);
                    }
                }
            });

        };

        $scope.newPublication = function () {
            $scope.myHidingValue=false;
            $uibModal.open({
                templateUrl: 'app/entities/publication/publication-dialog.html',
                controller: 'PublicationDialogController',
                size: 'lg',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: function () {
                        return {};
                    }
                }
            }).result.then(function (result) {
                $scope.publi = result;
            }, function () {
            })
        };

        $scope.newStudy = function () {
            $scope.myHidingValue=true;
            $uibModal.open({
                templateUrl: 'app/entities/study/study-dialog.html',
                controller: 'StudyDialogController',
                size: 'lg',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: function () {
                        return {};
                    }
                }
            }).result.then(function (result) {
                $scope.publi = result;
            }, function () {
            })
        };

        $scope.newSiteData = function () {
            $scope.myHidingValue=true;
            $uibModal.open({
                templateUrl: 'app/entities/site-data/site-data-dialog.html',
                controller: 'SiteDataDialogController',
                size: 'lg',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: function () {
                        return {};
                    }
                }
            }).result.then(function (result) {
                $scope.publi = result;
            }, function () {
            })
        };

        $scope.newTreatment = function () {
            $scope.myHidingValue=true;
            $uibModal.open({
                templateUrl: 'app/entities/treatment/treatment-dialog.html',
                controller: 'TreatmentDialogController',
                size: 'lg',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: function () {
                        return {};
                    }
                }
            }).result.then(function (result) {
                $scope.publi = result;
            }, function () {

            })
        };
    }

})();

//MAYBE INNECESSARY CODE, YET TO DETERMINE

/*for (var i=0; i<=$scope.publi.studies.length; i++){
 if($scope.studies[i]!=undefined) {
 $scope.publications.push($scope.studies[i].publicationss);
 }
 }
 for (var i=0; i<=$scope.publications[0].length; i++){
 if($scope.publications[0][i]!=undefined) {
 $scope.publicationsHTML.push($scope.publications[0][i]);
 }
 }*/
