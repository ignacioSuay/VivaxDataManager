(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('PublicationDialogController', PublicationDialogController);

    PublicationDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Publication', 'Study'];

    function PublicationDialogController ($scope, $stateParams, $uibModalInstance, entity, Publication, Study) {
        var vm = this;
        vm.publication = entity;
        vm.studys = Study.query();
        vm.load = function(id) {
            Publication.get({id : id}, function(result) {
                vm.publication = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('vivaxDataManagerApp:publicationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            alert('You must first erase any study that includes this publication');
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.publication.id !== null) {
                Publication.update(vm.publication, onSaveSuccess, onSaveError);
            } else {
                Publication.save(vm.publication, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

    }
})();
