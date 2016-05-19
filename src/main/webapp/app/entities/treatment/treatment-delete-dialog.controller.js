(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('TreatmentDeleteController',TreatmentDeleteController);

    TreatmentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Treatment', 'ShareDataService'];

    function TreatmentDeleteController($uibModalInstance, entity, Treatment, ShareDataService) {
        var vm = this;
        vm.treatment = entity;
        var flag = ShareDataService.getFlag();
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        if (!flag) {
            vm.confirmDelete = function (id) {
                console.log('In main menu delete button');
                Treatment.delete({id: id},
                    function () {
                        $uibModalInstance.close(true);
                    });
            };
        }
        else {
            var treatmentId = ShareDataService.getObject().id;
            vm.confirmDelete = function (id) {
                Treatment.delete({id: treatmentId},
                    function () {
                        $uibModalInstance.close(true);
                    });
            };
        }
    }
})();
