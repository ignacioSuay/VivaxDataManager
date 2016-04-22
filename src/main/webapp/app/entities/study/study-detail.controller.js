(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .controller('StudyDetailController', StudyDetailController);

    StudyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Study', 'Publication', 'SiteData'];

    function StudyDetailController($scope, $rootScope, $stateParams, entity, Study, Publication, SiteData) {
        var vm = this;
        vm.study = entity;
        vm.load = function (id) {
            Study.get({id: id}, function(result) {
                vm.study = result;
            });
        };
        var unsubscribe = $rootScope.$on('vivaxDataManagerApp:studyUpdate', function(event, result) {
            vm.study = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
