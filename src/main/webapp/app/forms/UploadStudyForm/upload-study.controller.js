(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('UploadStudyController', UploadStudyController);

    UploadStudyController.$inject = ['$scope', '$state', '$http', 'Form', '$uibModal', 'SiteData', 'StudyDTO', 'ShareDataService', 'Category', 'LocationHttp', 'TreatmentHttp', 'Publication', 'Location'];

    function UploadStudyController ($scope, $state, $http, Form, $uibModal, SiteData, StudyDTO, ShareDataService, Category, LocationHttp, TreatmentHttp, Publication, Location) {

        $scope.publi;
        $scope.pubMedId;
        $scope.newTreat;
        $scope.countryId;
        $scope.allPublications = Publication.queryAll();

        var flag=true;
        $scope.newTreatments = [{}];

        StudyDTO.getStudyTypes().then(function (result) {
            $scope.types = result.data;
        });
        LocationHttp.getDistinctCountries().then(function (result) {
            $scope.countries = result.data;
        });
        LocationHttp.getDistinctLocations().then(function (result) {
            $scope.allLocations = result.data;
        });
        TreatmentHttp.getDistinctTreatments().then(function (result) {
            $scope.treatments = result.data;
        });

        $scope.categorys = Category.query();

        $scope.retrievePublicationByPubMedId = function() {
            Form.load($scope.publication.pubMedId).then(function (result) {
                loadDataByFormDTO(result.data);
            });
        };

        $scope.saveAll = function(){
            Form.save($scope.publi).then(function(result){
                console.log($scope.publi);
                console.log(result.data);
                loadDataByFormDTO(result.data);
            });
        };

        var loadDataByFormDTO = function (formDTO){
            $scope.publi = formDTO;
            $scope.myHidingValue=true;
            $scope.hideSelectLocation=true;
            $scope.selectLocation=true;
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
                $scope.publication = result;
                $scope.retrievePublicationByPubMedId();
                $scope.myHidingValue=true;
                console.log('The pubMedId '+result.pubMedId);
            }, function () {
            })
        };

        $scope.setTreatment = function(item){
            $scope.newTreat = item;
        }

        $scope.addTreatments = function (siteData) {
            for (var i=0; i<siteData.treatments.length; i++) {
                console.log(i);
                if ($scope.newTreat.id === siteData.treatments[i].id) {
                    alert('Sorry, duplicated treatment!');
                    flag = false;
                }
            }
            if(flag===true){
                for (var i = 0; i < $scope.newTreatments.length; i++) {
                    if (i > 0) {
                        console.log(i);
                        if ($scope.newTreatments[i].id !== undefined) {
                            if ($scope.newTreatments[i].id === $scope.newTreat.id) {
                                flag = false;
                                alert('Sorry, duplicated treatment!');
                                $scope.newTreatments.splice(i, 1);
                            }
                        }
                        else {
                            flag = true;
                        }
                    }
                    else {
                        flag = true;
                    }
                }
            }
            if(flag===true){
                $scope.newTreatments.push({});
                console.log($scope.newTreatments);
                siteData.treatments.push($scope.newTreat);
            }
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

        $scope.newLocation = function(siteData) {
            ShareDataService.setFlag(true);
            ShareDataService.setObject(siteData);
            $scope.myHidingValue=true;
            $uibModal.open({
                templateUrl: 'app/entities/location/location-dialog.html',
                controller: 'LocationDialogController',
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
                siteData.location=result;
                console.log(result.data);
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
                        return {
                            study:studyDTO.studyDetails.ref
                        };
                    }
                }
            }).result.then(function (result) {
                studyDTO.siteDatas.push(result);
                ShareDataService.setFlag(false);
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
                siteData.treatments.push(result);
                ShareDataService.setFlag(false);
            }, function () {
            })
        };

        $scope.deleteTreatment = function(index, siteData) {
            siteData.treatments.splice(index, 1);
        };
    }
})();
