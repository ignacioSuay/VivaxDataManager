(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('SiteDataDeleteController',SiteDataDeleteController);

    SiteDataDeleteController.$inject = ['$uibModalInstance', 'entity', 'SiteData', 'ShareDataService'];

    function SiteDataDeleteController($uibModalInstance, entity, SiteData, ShareDataService) {
        var vm = this;
        vm.siteData = entity;
        var flag = ShareDataService.getFlag();
        var siteDataId = ShareDataService.getSiteData().id;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        if (!flag) {
            vm.confirmDelete = function (id) {
                SiteData.delete({id: id},
                    function () {
                        $uibModalInstance.close(true);
                    });
            };
        }
        else {
            vm.confirmDelete = function (id) {
                SiteData.delete({id: siteDataId},
                    function () {
                        $uibModalInstance.close(true);
                    });
            };
        }
    }
})();
