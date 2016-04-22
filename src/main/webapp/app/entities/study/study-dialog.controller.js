(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('StudyDialogController', StudyDialogController);

    StudyDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Study', 'Publication', 'SiteData'];

    function StudyDialogController ($scope, $stateParams, $uibModalInstance, entity, Study, Publication, SiteData) {
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
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.study.id !== null) {
                Study.update(vm.study, onSaveSuccess, onSaveError);
            } else {
                Study.save(vm.study, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
