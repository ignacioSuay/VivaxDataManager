(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('SiteDataDeleteController',SiteDataDeleteController);

    SiteDataDeleteController.$inject = ['$uibModalInstance', 'entity', 'SiteData'];

    function SiteDataDeleteController($uibModalInstance, entity, SiteData) {
        var vm = this;
        vm.siteData = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            SiteData.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
