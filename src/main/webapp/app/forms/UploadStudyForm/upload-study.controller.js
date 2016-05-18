(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('UploadStudyController', UploadStudyController);

    UploadStudyController.$inject = ['$scope', '$state', '$http', 'Form', '$uibModal', 'SiteData', 'ShareDataService'];
    console.log('In publication controller');

    function UploadStudyController ($scope, $state, $http, Form, $uibModal, SiteData, ShareDataService) {

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
            });
        };

        $scope.saveAll = function(){
            console.log($scope.publi);
            Form.save($scope.publi);
        }

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
                $scope.retrievePublicationByPubMedId();
                $scope.myHidingValue=true;
                console.log('The pubMedId '+result.pubMedId);
            }, function () {
            })
        };

        $scope.newStudy = function(index) {
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
                result.publicationss.push($scope.publi.publication);
                $scope.publi.studies.push(result);
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
                $scope.publi.siteDatas[0].push(result);
            }, function () {
            })
        };

        $scope.deleteSiteData = function(siteData) {
            ShareDataService.setSiteData(siteData);
            ShareDataService.setFlag(true);
            $uibModal.open({
                templateUrl: 'app/entities/site-data/site-data-delete-dialog.html',
                controller: 'SiteDataDeleteController',
                size: 'lg',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: function () {
                        return {};
                    }
                }
            }).result.then(function (result) {
                alert('Ich bin god');
            }, function () {
            })
        };

        $scope.newTreatment = function (index) {
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
                $scope.publi.siteDatas[0][index].treatments.push(result);
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
 }
 for (var i=0; i<=$scope.publi.treatments[0].length; i++){
 if($scope.publi.treatments[0][i]!=undefined) {
 $scope.treatments.push($scope.publi.treatments[0][i]);
 }
 }

 function create2DArray(rows,columns) {
 var x = new Array(rows);
 for (var i = 0; i < rows; i++) {
 x[i] = new Array(columns);
 }
 return x;
 }

 TODO make this code reusable for all Classes
$scope.updateSiteData = function (){
    var siteData = ShareDataService.getSiteData();
    UpdateTreatmentList.update(siteData);
};
*/
