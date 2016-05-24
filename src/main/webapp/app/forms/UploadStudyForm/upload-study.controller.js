(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('UploadStudyController', UploadStudyController);

    UploadStudyController.$inject = ['$scope', '$state', '$http', 'Form', '$uibModal', 'SiteData', 'ShareDataService'];

    function UploadStudyController ($scope, $state, $http, Form, $uibModal, SiteData, ShareDataService) {

        $scope.publi;
        $scope.pubMedId;

        $scope.retrievePublicationByPubMedId = function () {
            Form.load($scope.pubMedId).then(function (result) {
                $scope.publi = result.data;
                $scope.myHidingValue=true;
            });
        };

        $scope.saveAll = function(){
            console.log($scope.publi);
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
                $scope.pubMedId = result.pubMedId;
                console.log(result);
                $scope.retrievePublicationByPubMedId();
                $scope.myHidingValue=true;
                console.log('The pubMedId '+result.pubMedId);
            }, function () {
            })
        };

        $scope.newStudy = function(studyDTOList) {
            ShareDataService.setFlag(true);
            ShareDataService.setObject(studyDTOList);
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
                result.data.studyDetails.publicationss.push($scope.publi.publication);
                $scope.publi.studyDTOList.push(result.data);
                ShareDataService.setFlag(false);
            }, function () {
            })
        };

        $scope.deleteStudy = function(studyDTO) {
            ShareDataService.setFlag(true);
            ShareDataService.setPubli($scope.publi);
            ShareDataService.setObject(studyDTO);
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

        $scope.newSiteData = function (studyDTO) {
            ShareDataService.setFlag(true);
            ShareDataService.setPubli($scope.publi);
            ShareDataService.setObject(null);
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
                studyDTO.siteDatas.push(ShareDataService.getObject());
                ShareDataService.setFlag(false);
                console.log($scope.publi.studyDTOList);
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

        $scope.newTreatment = function (index, siteData) {
            ShareDataService.setFlag(true);
            ShareDataService.setObject(siteData);
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
                siteData = ShareDataService.getObject();
                ShareDataService.setFlag(false);
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
