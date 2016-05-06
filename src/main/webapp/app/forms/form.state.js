(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('forms', {
            abstract: true,
            parent: 'app'
        });
    }
})();
