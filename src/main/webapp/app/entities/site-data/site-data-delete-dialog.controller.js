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
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        if (!flag) {
            vm.confirmDelete = function (id) {
                console.log('In main menu delete button');
                SiteData.delete({id: id},
                    function () {
                        $uibModalInstance.close(true);
                    });
            };
        }
        else {
            var siteDataId = ShareDataService.getObject().id;
            console.log('In not main menu delete button');
            vm.confirmDelete = function (id) {
                SiteData.delete({id: siteDataId},
                    function () {
                        $uibModalInstance.close(true);
                    });
            };
        }
    }
})();
