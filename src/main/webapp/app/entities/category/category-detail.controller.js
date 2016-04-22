(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('CategoryDetailController', CategoryDetailController);

    CategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Category', 'SiteData'];

    function CategoryDetailController($scope, $rootScope, $stateParams, entity, Category, SiteData) {
        var vm = this;
        vm.category = entity;
        vm.load = function (id) {
            Category.get({id: id}, function(result) {
                vm.category = result;
            });
        };
        var unsubscribe = $rootScope.$on('vivaxDataManagerApp:categoryUpdate', function(event, result) {
            vm.category = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
