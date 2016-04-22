(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('SiteDataDialogController', SiteDataDialogController);

    SiteDataDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'SiteData', 'Category', 'Location', 'Study', 'Treatment'];

    function SiteDataDialogController ($scope, $stateParams, $uibModalInstance, entity, SiteData, Category, Location, Study, Treatment) {
        var vm = this;
        vm.siteData = entity;
        vm.categorys = Category.query();
        vm.locations = Location.query();
        vm.studys = Study.query();
        vm.treatments = Treatment.query();
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

        vm.save = function () {
            vm.isSaving = true;
            if (vm.siteData.id !== null) {
                SiteData.update(vm.siteData, onSaveSuccess, onSaveError);
            } else {
                SiteData.save(vm.siteData, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
