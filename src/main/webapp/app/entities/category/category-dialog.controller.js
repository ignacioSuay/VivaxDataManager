(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('CategoryDialogController', CategoryDialogController);

    CategoryDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Category', 'SiteData'];

    function CategoryDialogController ($scope, $stateParams, $uibModalInstance, entity, Category, SiteData) {
        var vm = this;
        vm.category = entity;
        vm.sitedatas = SiteData.query();
        vm.load = function(id) {
            Category.get({id : id}, function(result) {
                vm.category = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('vivaxDataManagerApp:categoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.category.id !== null) {
                Category.update(vm.category, onSaveSuccess, onSaveError);
            } else {
                Category.save(vm.category, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
