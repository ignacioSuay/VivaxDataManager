(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('UploadStudyController', UploadStudyController);

    UploadStudyController.$inject = ['$scope', '$state', '$http', 'Form', '$uibModal', 'SiteData', 'ShareDataService'];

    function UploadStudyController ($scope, $state, $http, Form, $uibModal, SiteData, ShareDataService) {

        $scope.publi;
        $scope.pubMedId;
        $scope.studies = [];
        $scope.siteDatas = [];
        $scope.treatments = [];

        $scope.retrievePublicationByPubMedId = function () {
            Form.load($scope.pubMedId).then(function (result) {
                console.log(result);
                $scope.publi = result.data;
                $scope.myHidingValue=true;
                $scope.studies=$scope.publi.studies;
                console.log($scope.publi);
                for (var i=0; i<=$scope.publi.siteDatas[0].length; i++){
                    if($scope.publi.siteDatas[0][i]!=undefined) {
                        $scope.siteDatas.push($scope.publi.siteDatas[0][i]);
                    }
                }
            });
        };

        $scope.saveAll = function(){
            //console.log($scope.publi);
            console.log(Form.save($scope.publi));
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

        $scope.deleteStudy = function(study) {
            ShareDataService.setFlag(true);
            ShareDataService.setPubli($scope.publi);
            ShareDataService.setObject(study);
            $uibModal.open({
                templateUrl: 'app/entities/study/study-delete-dialog.html',
                controller: 'StudyDeleteController',
                size: 'lg',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: function () {
                        return {};
                    }
                }
            }).result.then(function (result) {
                $scope.publi=ShareDataService.getPubli();
                ShareDataService.setFlag(false);
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
            ShareDataService.setFlag(true);
            ShareDataService.setPubli($scope.publi);
            ShareDataService.setObject(siteData);
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
                $scope.publi=ShareDataService.getPubli();
                ShareDataService.setFlag(false);
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

        $scope.deleteTreatment = function(treatment) {
            ShareDataService.setFlag(true);
            ShareDataService.setObject(treatment);
            ShareDataService.setPubli($scope.publi);
            $uibModal.open({
                templateUrl: 'app/entities/treatment/treatment-delete-dialog.html',
                controller: 'TreatmentDeleteController',
                size: 'lg',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    entity: function () {
                        return {};
                    }
                }
            }).result.then(function (result) {
                $scope.publi=ShareDataService.getPubli();
                ShareDataService.setFlag(false);
            }, function () {
            })
        };
    }
})();

