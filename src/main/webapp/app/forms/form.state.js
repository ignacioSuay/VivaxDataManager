angular.module('vivaxDataManagerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('forms', {
                abstract: true,
                parent: 'app'
            });
    });
