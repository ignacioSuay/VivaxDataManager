(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('TreatmentDialogController', TreatmentDialogController);

    TreatmentDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Treatment', 'SiteData', 'ShareDataService'];

    function TreatmentDialogController ($scope, $stateParams, $uibModalInstance, entity, Treatment, SiteData, ShareDataService) {

        var vm = this;
        vm.treatment = entity;
        vm.sitedatas = SiteData.query();
        vm.load = function(id) {
            Treatment.get({id : id}, function(result) {
                vm.treatment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('vivaxDataManagerApp:treatmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };
        /**
         * Option for when save is called from the entities menu
         */
        vm.save = function () {
            vm.isSaving = true;
            if (vm.treatment.id !== null) {
                Treatment.update(vm.treatment, onSaveSuccess, onSaveError);
            } else {
                Treatment.save(vm.treatment, onSaveSuccess, onSaveError);
            }
        };
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
