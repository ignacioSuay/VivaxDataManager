(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('LocationDialogController', LocationDialogController);

    LocationDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Location', 'SiteData', 'ShareDataService'];

    function LocationDialogController ($scope, $stateParams, $uibModalInstance, entity, Location, SiteData, ShareDataService) {
        var vm = this;
        vm.location = entity;
        vm.sitedatas = SiteData.query();
        vm.load = function(id) {
            Location.get({id : id}, function(result) {
                vm.location = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('vivaxDataManagerApp:locationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.location.id !== null) {
                Location.update(vm.location, onSaveSuccess, onSaveError);
            } else {
                Location.save(vm.location, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
