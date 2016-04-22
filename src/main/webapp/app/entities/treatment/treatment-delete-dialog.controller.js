(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('TreatmentDeleteController',TreatmentDeleteController);

    TreatmentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Treatment'];

    function TreatmentDeleteController($uibModalInstance, entity, Treatment) {
        var vm = this;
        vm.treatment = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Treatment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
