(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider
            .state('studyUpload', {
                parent: 'forms',
                url: '/studyUpload',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Study Upload Data'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/forms/UploadStudyForm/upload-study-form.html',
                        controller: 'UploadStudyController',
                        controllerAs: 'vm'
                    }
                }

            });
    }}());
