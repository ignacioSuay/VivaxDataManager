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
                Treatment.delete({id: id},
                    function () {
                        $uibModalInstance.close(true);
                    });
            };
        }
        else {
            var treatment = ShareDataService.getObject();
            var publi = ShareDataService.getPubli();
            vm.confirmDelete = function (id) {
                for (var i=0; i<=publi.siteDatas[0].length; i++) {
                    if (publi.siteDatas[0][i] !== undefined) {
                        var tLength = publi.siteDatas[0][i].treatments.length;
                        for (var j = 0; j <= tLength; j++) {
                            if(publi.siteDatas[0][i].treatments[j] !== undefined) {
                                if (publi.siteDatas[0][i].treatments[j].id === treatment.id) {
                                    publi.siteDatas[0][i].treatments.splice(j, 1);
                                }
                            }
                        }
                    }
                }
                ShareDataService.setPubli(publi);
                Treatment.delete({id: treatment.id
                },
                    function () {
                        $uibModalInstance.close(true);
                       
                    });
            };
        }
    }
})();


