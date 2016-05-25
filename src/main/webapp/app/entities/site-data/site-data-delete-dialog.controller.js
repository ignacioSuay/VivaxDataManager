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
        /**
         * Option for when delete is called from the entities menu
         */
        if (!flag) {
            vm.confirmDelete = function (id) {
                SiteData.delete({id: id},
                    function () {
                        $uibModalInstance.close(true);
                    });
            };
        }
        /**
         * Option for when delete is called from the upload Study menu
         */
        else {
            var siteData = ShareDataService.getObject();
            var publi = ShareDataService.getPubli();
            vm.confirmDelete = function (id) {
                for (var i=0; i<=publi.studyDTOList.length; i++) {
                    if (publi.studyDTOList[i] !== undefined) {
                        publi.studyDTOList[i].siteDatas.splice(siteData, 1);
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
