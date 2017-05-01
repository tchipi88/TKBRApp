(function () {
    'use strict';
    angular
            .module('app')
            .config(stateConfig);
    stateConfig.$inject = ['$stateProvider'];
    function stateConfig($stateProvider) {
        $stateProvider
                .state('terrain-reglement', {
                    parent: 'entity',
                    url: '/terrain-reglement?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/terrain-reglement/terrain-reglements.html',
                            controller: 'TerrainReglementController',
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
                .state('terrain-reglement-detail', {
                    parent: 'terrain-reglement',
                    url: '/terrain-reglement/{id}',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/entities/terrain-reglement/terrain-reglement-detail.html',
                            controller: 'TerrainReglementDetailController',
                            controllerAs: 'vm'}
                    },
                    resolve: {
                        entity: ['$stateParams', 'TerrainReglement', function ($stateParams, TerrainReglement) {
                                return TerrainReglement.get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'terrain-reglement',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('terrain-reglement-detail.edit', {
                    parent: 'terrain-reglement-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/terrain-reglement/terrain-reglement-dialog.html',
                                controller: 'TerrainReglementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['TerrainReglement', function (TerrainReglement) {
                                            return TerrainReglement.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('terrain-reglement.new', {
                    parent: 'terrain-reglement',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/terrain-reglement/terrain-reglement-dialog.html',
                                controller: 'TerrainReglementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: function () {
                                        return {
                                            dateVersement: new Date(),
                                            mode: 'ESPECES'
                                        };
                                    }
                                }
                            }).result.then(function () {
                                $state.go('terrain-reglement', null, {reload: 'terrain-reglement'});
                            }, function () {
                                $state.go('terrain-reglement');
                            });
                        }]
                })
                .state('terrain-reglement.edit', {
                    parent: 'terrain-reglement',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/terrain-reglement/terrain-reglement-dialog.html',
                                controller: 'TerrainReglementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['TerrainReglement', function (TerrainReglement) {
                                            return TerrainReglement.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('terrain-reglement', null, {reload: 'terrain-reglement'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('terrain-reglement.delete', {
                    parent: 'terrain-reglement',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/terrain-reglement/terrain-reglement-delete-dialog.html',
                                controller: 'TerrainReglementDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['TerrainReglement', function (TerrainReglement) {
                                            return TerrainReglement.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('terrain-reglement', null, {reload: 'terrain-reglement'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
