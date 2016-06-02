(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('SiteDataDialogController', SiteDataDialogController);

    SiteDataDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SiteData', 'Category', 'Location', 'Study', 'Treatment', 'ShareDataService', 'LocationHttp', 'TreatmentHttp', 'StudyDTO'];

    function SiteDataDialogController ($scope, $stateParams, $uibModalInstance, entity, SiteData, Category, Location, Study, Treatment, ShareDataService, LocationHttp, TreatmentHttp, StudyDTO) {
        var vm = this;
        vm.siteData = entity;
        vm.categorys = Category.query();
        LocationHttp.getDistinctLocations().then(function (result) {
            vm.locations = result.data;
        });
        TreatmentHttp.getDistinctTreatments().then(function (result) {
            vm.treatments = result.data;
        });
        StudyDTO.getAllStudiesNonPaged().then(function(result){
            vm.studys = result.data;
        });
        vm.load = function(id) {
            SiteData.get({id : id}, function(result) {
                vm.siteData = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('vivaxDataManagerApp:siteDataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        $scope.setTreatment = function(item){
            $scope.newTreat = item;
        }

        /**
         * Option for when save is called from the entities menu
         */
            vm.save = function () {
                vm.isSaving = true;
                if (vm.siteData.id !== null) {
                    SiteData.update(vm.siteData, onSaveSuccess, onSaveError);
                } else {
                    SiteData.save(vm.siteData, onSaveSuccess, onSaveError);
                }
            };

        /**
         * Option for when save is called from the upload Study menu
         */
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
