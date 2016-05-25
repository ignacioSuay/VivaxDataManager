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
        /**
         * Option for when delete is called from the entities menu
         */
        if (!flag) {
            vm.confirmDelete = function (id) {
                Study.delete({id: id},
                    function () {
                        $uibModalInstance.close(true);
                    });
                };
            }
        /**
         * Option for when delete is called from the upload Study form
         */
        else {
            var studyDTO = ShareDataService.getObject();
            var publi = ShareDataService.getPubli();
            vm.confirmDelete = function (id) {
                for (var i=0; i<=publi.studyDTOList.length; i++) {
                    if (publi.studyDTOList[i] !== undefined) {
                        publi.studyDTOList.splice(studyDTO, 1);
                    }
                }
                ShareDataService.setPubli(publi);
                var study = studyDTO.studies;
                var siteDatas = studyDTO.siteDatas;
                study.siteDatas = siteDatas;
                Study.delete({id: study.id
                    },
                    function () {
                        $uibModalInstance.close(true);
                    });
                };
            }
        }
})();
