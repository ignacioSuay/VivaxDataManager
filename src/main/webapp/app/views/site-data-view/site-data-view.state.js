(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
    	
        $stateProvider
        .state('siteDataView', {
            parent: 'views',
            url: '/siteDataView',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SiteDataView'
            },
            views: {
                'content@': {
                    templateUrl: 'app/views/site-data-view/site-data-view.html',
                    controller: 'SiteDataViewController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                    	page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
            
})

        .state('siteDataView.detail', {
            parent: 'views',
            url: '/siteDataView/filter',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SiteDataView'
            },
            views: {
                'content@': {
                    templateUrl: 'app/views/site-data-view/site-data-view.html',
                    controller: 'SiteDataViewController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                    	page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
})
}}());