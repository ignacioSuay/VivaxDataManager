(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('TreatmentDetailController', TreatmentDetailController);

    TreatmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Treatment', 'SiteData'];

    function TreatmentDetailController($scope, $rootScope, $stateParams, entity, Treatment, SiteData) {
        var vm = this;
        vm.treatment = entity;
        vm.load = function (id) {
            Treatment.get({id: id}, function(result) {
                vm.treatment = result;
            });
        };
        var unsubscribe = $rootScope.$on('vivaxDataManagerApp:treatmentUpdate', function(event, result) {
            vm.treatment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
