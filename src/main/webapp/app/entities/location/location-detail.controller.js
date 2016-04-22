(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('LocationDetailController', LocationDetailController);

    LocationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Location', 'SiteData'];

    function LocationDetailController($scope, $rootScope, $stateParams, entity, Location, SiteData) {
        var vm = this;
        vm.location = entity;
        vm.load = function (id) {
            Location.get({id: id}, function(result) {
                vm.location = result;
            });
        };
        var unsubscribe = $rootScope.$on('vivaxDataManagerApp:locationUpdate', function(event, result) {
            vm.location = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
