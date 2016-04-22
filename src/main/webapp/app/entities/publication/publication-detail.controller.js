(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('PublicationDetailController', PublicationDetailController);

    PublicationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Publication', 'Study'];

    function PublicationDetailController($scope, $rootScope, $stateParams, entity, Publication, Study) {
        var vm = this;
        vm.publication = entity;
        vm.load = function (id) {
            Publication.get({id: id}, function(result) {
                vm.publication = result;
            });
        };
        var unsubscribe = $rootScope.$on('vivaxDataManagerApp:publicationUpdate', function(event, result) {
            vm.publication = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
