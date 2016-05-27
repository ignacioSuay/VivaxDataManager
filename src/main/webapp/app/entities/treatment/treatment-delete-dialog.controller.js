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
        /**
         * Option for when delete is called from the entities menu
         */
        if (!flag) {
            vm.confirmDelete = function (id) {
                Treatment.delete({id: id},
                    function () {
                        $uibModalInstance.close(true);
                    });
            };
        }
        /**
         * Option for when delete is called from the upload Study menu
         */
        else {
            var treatment = ShareDataService.getObject();
            var publi = ShareDataService.getPubli();
            vm.confirmDelete = function (id) {
                for (var i=0; i<=publi.studyDTOList.length; i++) {
                    if (publi.studyDTOList[i] !== undefined) {
                        var sLength = publi.studyDTOList[i].siteDatas.length;
                        for (var j = 0; j <= sLength; j++) {
                            if(publi.studyDTOList[i].siteDatas[j] !== undefined) {
                                var tLength = publi.studyDTOList[i].siteDatas[j].treatments.length;
                                for (var k = 0; k <= tLength; k++) {
                                    if (publi.studyDTOList[i].siteDatas[j].treatments[k] !== undefined){
                                        if (publi.studyDTOList[i].siteDatas[j].treatments[k].id === treatment.id) {
                                            publi.studyDTOList[i].siteDatas[j].treatments.splice(k, 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                ShareDataService.setPubli(publi);
                $uibModalInstance.close(true);
            };
        }
    }
})();
