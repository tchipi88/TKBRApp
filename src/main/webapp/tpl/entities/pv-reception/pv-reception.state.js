(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pv-reception', {
            parent: 'entity',
            url: '/pv-reception?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tkbrApp.pvReception.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pv-reception/pv-receptions.html',
                    controller: 'PvReceptionController',
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
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pvReception');
                    $translatePartialLoader.addPart('etatPv');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pv-reception-detail', {
            parent: 'pv-reception',
            url: '/pv-reception/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tkbrApp.pvReception.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pv-reception/pv-reception-detail.html',
                    controller: 'PvReceptionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pvReception');
                    $translatePartialLoader.addPart('etatPv');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PvReception', function($stateParams, PvReception) {
                    return PvReception.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pv-reception',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pv-reception-detail.edit', {
            parent: 'pv-reception-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pv-reception/pv-reception-dialog.html',
                    controller: 'PvReceptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PvReception', function(PvReception) {
                            return PvReception.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pv-reception.new', {
            parent: 'pv-reception',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pv-reception/pv-reception-dialog.html',
                    controller: 'PvReceptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                reference: null,
                                dateReception: null,
                                etat: null,
                                commentaires: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pv-reception', null, { reload: 'pv-reception' });
                }, function() {
                    $state.go('pv-reception');
                });
            }]
        })
        .state('pv-reception.edit', {
            parent: 'pv-reception',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pv-reception/pv-reception-dialog.html',
                    controller: 'PvReceptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PvReception', function(PvReception) {
                            return PvReception.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pv-reception', null, { reload: 'pv-reception' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pv-reception.delete', {
            parent: 'pv-reception',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pv-reception/pv-reception-delete-dialog.html',
                    controller: 'PvReceptionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PvReception', function(PvReception) {
                            return PvReception.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pv-reception', null, { reload: 'pv-reception' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
