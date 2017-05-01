(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('terrain-suivi', {
                parent: 'entity',
                        url: '/terrain-suivi?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/terrain-suivi/terrain-suivis.html',
                                controller: 'TerrainSuiviController',
                                controllerAs: 'vm'  }
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
                .state('terrain-suivi-detail', {
                parent: 'terrain-suivi',
                        url: '/terrain-suivi/{id}',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/terrain-suivi/terrain-suivi-detail.html',
                                controller: 'TerrainSuiviDetailController',
                                controllerAs: 'vm'}
                        },
                        resolve: {
                        entity: ['$stateParams', 'TerrainSuivi', function ($stateParams, TerrainSuivi) {
                        return TerrainSuivi.get({id: $stateParams.id}).$promise;
                        }],
                                previousState: ["$state", function ($state) {
                                var currentStateData = {
                                name: $state.current.name || 'terrain-suivi',
                                        params: $state.params,
                                        url: $state.href($state.current.name, $state.params)
                                };
                                        return currentStateData;
                                }]
                        }
                })
                .state('terrain-suivi-detail.edit', {
                parent: 'terrain-suivi-detail',
                        url: '/detail/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/terrain-suivi/terrain-suivi-dialog.html',
                                controller: 'TerrainSuiviDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['TerrainSuivi', function (TerrainSuivi) {
                                return TerrainSuivi.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('terrain-suivi.new', {
                parent: 'terrain-suivi',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/terrain-suivi/terrain-suivi-dialog.html',
                                controller: 'TerrainSuiviDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: function () {
                                return {

                                };
                                }
                                }
                        }).result.then(function () {
                        $state.go('terrain-suivi', null, {reload: 'terrain-suivi'});
                        }, function () {
                        $state.go('terrain-suivi');
                        });
                        }]
                })
                .state('terrain-suivi.edit', {
                parent: 'terrain-suivi',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/terrain-suivi/terrain-suivi-dialog.html',
                                controller: 'TerrainSuiviDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['TerrainSuivi', function (TerrainSuivi) {
                                return TerrainSuivi.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('terrain-suivi', null, {reload: 'terrain-suivi'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('terrain-suivi.delete', {
                parent: 'terrain-suivi',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/terrain-suivi/terrain-suivi-delete-dialog.html',
                                controller: 'TerrainSuiviDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['TerrainSuivi', function (TerrainSuivi) {
                                return TerrainSuivi.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('terrain-suivi', null, {reload: 'terrain-suivi'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
