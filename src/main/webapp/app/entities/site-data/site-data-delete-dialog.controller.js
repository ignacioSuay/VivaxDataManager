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
            var siteData = ShareDataService.getObject();
            var publi = ShareDataService.getPubli();
            vm.confirmDelete = function (id) {
                for (var i=0; i<=publi.siteDatas[0].length; i++) {
                    if (publi.siteDatas[0][i] !== undefined) {
                        if (publi.siteDatas[0][i].id === siteData.id) {
                            publi.siteDatas[0].splice(i, 1);
                        }
                    }
                }
                ShareDataService.setPubli(publi);
                SiteData.delete({id: siteData.id
                    },
                    function () {
                        $uibModalInstance.close(true);
                    });
            };
        }
    }
})();
