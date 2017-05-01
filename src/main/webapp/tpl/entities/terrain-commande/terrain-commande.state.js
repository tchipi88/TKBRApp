(function () {
    'use strict';
    angular
            .module('app')
            .config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('terrain-commande', {
                    parent: 'entity',
                    url: '/terrain-commande?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/terrain-commande/terrain-commandes.html',
                            controller: 'TerrainCommandeController',
                            controllerAs: 'vm'}
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
                        search: null,
                        type: {
                            value: 'ACHAT',
                            squash: true
                        }
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
                .state('terrain-commande-detail', {
                    parent: 'terrain-commande',
                    url: '/terrain-commande/{id}',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/terrain-commande/terrain-commande-detail.html',
                            controller: 'TerrainCommandeDetailController',
                            controllerAs: 'vm'}
                    },
                    resolve: {
                        entity: ['$stateParams', 'TerrainCommande', function ($stateParams, TerrainCommande) {
                                return TerrainCommande.get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'terrain-commande',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('terrain-commande-detail.edit', {
                    parent: 'terrain-commande-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/terrain-commande/terrain-commande-dialog.html',
                                controller: 'TerrainCommandeDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['TerrainCommande', function (TerrainCommande) {
                                            return TerrainCommande.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('terrain-commande.new', {
                    parent: 'terrain-commande',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/terrain-commande/terrain-commande-dialog.html',
                                controller: 'TerrainCommandeDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: function () {
                                        return {
                                            type: $stateParams.type,
                                            dateEmission: new Date()
                                        };
                                    },
                                    reglements: function () {
                                        return [];
                                    }
                                }
                            }).result.then(function () {
                                $state.go('terrain-commande', null, {reload: 'terrain-commande'});
                            }, function () {
                                $state.go('terrain-commande');
                            });
                        }]
                })
                .state('terrain-commande.edit', {
                    parent: 'terrain-commande',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/terrain-commande/terrain-commande-dialog.html',
                                controller: 'TerrainCommandeDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['TerrainCommande', function (TerrainCommande) {
                                            return TerrainCommande.get({id: $stateParams.id}).$promise;
                                        }],
                                    reglements: ['$resource', function ($resource) {
                                            return  $resource('api/terrain-reglementss/' + $stateParams.id).query();
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('terrain-commande', null, {reload: 'terrain-commande'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('terrain-commande.delete', {
                    parent: 'terrain-commande',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/terrain-commande/terrain-commande-delete-dialog.html',
                                controller: 'TerrainCommandeDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['TerrainCommande', function (TerrainCommande) {
                                            return TerrainCommande.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('terrain-commande', null, {reload: 'terrain-commande'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
