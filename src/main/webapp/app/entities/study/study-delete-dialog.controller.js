(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('StudyDeleteController',StudyDeleteController);

    StudyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Study', 'ShareDataService'];

    function StudyDeleteController($uibModalInstance, entity, Study, ShareDataService) {
        var vm = this;
        vm.study = entity;
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
            var study = ShareDataService.getObject();
            var publi = ShareDataService.getPubli();
            vm.confirmDelete = function (id) {
                for (var i=0; i<=publi.studies.length; i++) {
                    if (publi.studies[i] !== undefined) {
                        publi.studies.splice(i, 1);
                    }
                }
                ShareDataService.setPubli(publi);
                Study.delete({id: study.id
                    },
                    function () {
                        $uibModalInstance.close(true);

                    });
            };
        }
    }
})();
