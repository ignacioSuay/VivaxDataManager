(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('SiteDataDialogController', SiteDataDialogController);

    SiteDataDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SiteData', 'Category', 'Location', 'Study', 'Treatment', 'ShareDataService'];

    function SiteDataDialogController ($scope, $stateParams, $uibModalInstance, entity, SiteData, Category, Location, Study, Treatment, ShareDataService) {
        var vm = this;
        vm.siteData = entity;
        vm.categorys = Category.query();
        vm.locations = Location.query();
        vm.studys = Study.query();
        vm.treatmentss = Treatment.query();
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
        /**
         * Option for when save is called from the entities menu
         */
        if(!ShareDataService.getFlag()) {
            vm.save = function () {
                vm.isSaving = true;
                if (vm.siteData.id !== null) {
                    SiteData.update(vm.siteData, onSaveSuccess, onSaveError);
                } else {
                    SiteData.save(vm.siteData, onSaveSuccess, onSaveError);
                }
            };
        }
        /**
         * Option for when save is called from the upload Study menu
         */
        else {
            vm.save = function () {
                if(ShareDataService.getObject()===null){
                    var siteData = vm.siteData;
                    ShareDataService.setObject(siteData);
                    $uibModalInstance.close(siteData);
                }
            };
        }
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
