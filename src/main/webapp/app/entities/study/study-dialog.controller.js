(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('StudyDialogController', StudyDialogController);

    StudyDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Study', 'StudyDTO', 'Publication', 'SiteData', 'ShareDataService'];

    function StudyDialogController ($scope, $stateParams, $uibModalInstance, entity, Study, StudyDTO,Publication, SiteData, ShareDataService) {
        var vm = this;
        vm.study = entity;
        vm.publications = Publication.query();
        vm.sitedatas = SiteData.query();
        vm.load = function(id) {
            Study.get({id : id}, function(result) {
                vm.study = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('vivaxDataManagerApp:studyUpdate', result);
            console.log(result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };
        /**
         * Option for when save is called from the entities menu
         */
        if(!ShareDataService.getFlag()) {
            vm.save = function () {
                vm.isSaving = true;
                if (vm.study.id !== null) {
                    Study.update(vm.study, onSaveSuccess, onSaveError);
                } else {
                    Study.save(vm.study, onSaveSuccess, onSaveError);
                }
            };
        }
        /**
         * Option for when save is called from the upload Study form
         */
        else {
            vm.save = function () {
                var sDTO = StudyDTO.save(vm.study);
                $uibModalInstance.close(sDTO);
                vm.isSaving = false;
            }
        }
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        }
}})();
