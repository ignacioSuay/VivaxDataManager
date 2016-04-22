(function() {
    'use strict';

    angular
        .module('vivaxDataManagerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('treatment', {
            parent: 'entity',
            url: '/treatment?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Treatments'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/treatment/treatments.html',
                    controller: 'TreatmentController',
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
        .state('treatment-detail', {
            parent: 'entity',
            url: '/treatment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Treatment'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/treatment/treatment-detail.html',
                    controller: 'TreatmentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Treatment', function($stateParams, Treatment) {
                    return Treatment.get({id : $stateParams.id});
                }]
            }
        })
        .state('treatment.new', {
            parent: 'treatment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/treatment/treatment-dialog.html',
                    controller: 'TreatmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                treatmentName: null,
                                treatmentArmCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('treatment', null, { reload: true });
                }, function() {
                    $state.go('treatment');
                });
            }]
        })
        .state('treatment.edit', {
            parent: 'treatment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/treatment/treatment-dialog.html',
                    controller: 'TreatmentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Treatment', function(Treatment) {
                            return Treatment.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('treatment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('treatment.delete', {
            parent: 'treatment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/treatment/treatment-delete-dialog.html',
                    controller: 'TreatmentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Treatment', function(Treatment) {
                            return Treatment.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('treatment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
