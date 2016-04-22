(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('SiteDataDetailController', SiteDataDetailController);

    SiteDataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'SiteData', 'Category', 'Location', 'Study', 'Treatment'];

    function SiteDataDetailController($scope, $rootScope, $stateParams, entity, SiteData, Category, Location, Study, Treatment) {
        var vm = this;
        vm.siteData = entity;
        vm.load = function (id) {
            SiteData.get({id: id}, function(result) {
                vm.siteData = result;
            });
        };
        var unsubscribe = $rootScope.$on('vivaxDataManagerApp:siteDataUpdate', function(event, result) {
            vm.siteData = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
