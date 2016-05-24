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
        else {
            vm.save = function () {
                if(ShareDataService.getObject()===null){
                    var siteData = vm.siteData;
                    ShareDataService.setObject(siteData);
                    $uibModalInstance.close(siteData);
                }



                /*vm.isSaving = true;
                if (vm.treatment.id !== undefined) {
                    console.log(vm.treatment.id);
                    Treatment.update(vm.treatment, onSaveSuccess, onSaveError);
                } else {
                    Treatment.save(vm.treatment, onSaveSuccess, onSaveError);
                }*/
            };
        }

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
