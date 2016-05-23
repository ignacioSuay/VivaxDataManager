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
                Study.delete({id: id},
                    function () {
                        $uibModalInstance.close(true);
                    });
                };
            }

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
