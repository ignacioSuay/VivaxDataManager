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

        var onSaveSuccess2 = function (result) {
            console.log(result);
            var siteData = ShareDataService.getSiteData();
            var tre = {id: result.id, treatmentName: result.treatmentName, treatmentArmCode : result.treatmentArmCode, version : result.version};
            siteData.treatments.push(tre);
            ShareDataService.setSiteData(siteData);
            $scope.$emit('vivaxDataManagerApp:treatmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        $scope.save2 = function (treatment) {
            vm.isSaving = true;
            var flag = ShareDataService.getFlag();
            if (treatment.id !== null && treatment.id!==undefined) {
                Treatment.update(treatment, onSaveSuccess, onSaveError);
            }
            else if (flag === 0){
                Treatment.save(treatment, onSaveSuccess2, onSaveError);
            }
            else {
                Treatment.save(treatment, onSaveSuccess, onSaveError);
            }
            return $scope.treatment;
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
