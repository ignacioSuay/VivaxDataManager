angular.module('vivaxDataManagerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('views', {
                abstract: true,
                parent: 'app'
            });
    });