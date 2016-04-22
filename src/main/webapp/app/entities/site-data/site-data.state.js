(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('site-data', {
            parent: 'entity',
            url: '/site-data?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SiteData'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/site-data/site-data.html',
                    controller: 'SiteDataController',
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
        .state('site-data-detail', {
            parent: 'entity',
            url: '/site-data/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'SiteData'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/site-data/site-data-detail.html',
                    controller: 'SiteDataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'SiteData', function($stateParams, SiteData) {
                    return SiteData.get({id : $stateParams.id});
                }]
            }
        })
        .state('site-data.new', {
            parent: 'site-data',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/site-data/site-data-dialog.html',
                    controller: 'SiteDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                comments: null,
                                day28Recurrence: null,
                                lowest95CI: null,
                                numEnroled: null,
                                numPatientsTreatCQ: null,
                                primaquine: null,
                                timePrimaquine: null,
                                typeStudy: null,
                                upper95CI: null,
                                yearEnd: null,
                                yearStart: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('site-data', null, { reload: true });
                }, function() {
                    $state.go('site-data');
                });
            }]
        })
        .state('site-data.edit', {
            parent: 'site-data',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/site-data/site-data-dialog.html',
                    controller: 'SiteDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SiteData', function(SiteData) {
                            return SiteData.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('site-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('site-data.delete', {
            parent: 'site-data',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/site-data/site-data-delete-dialog.html',
                    controller: 'SiteDataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SiteData', function(SiteData) {
                            return SiteData.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('site-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
