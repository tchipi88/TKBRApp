(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('compte-analytique-client', {
                parent: 'entity',
                        url: '/compte-analytique-client?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/compte-analytique-client/compte-analytique-clients.html',
                                controller: 'CompteAnalytiqueClientController',
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
                .state('compte-analytique-client-detail', {
                parent: 'compte-analytique-client',
                        url: '/compte-analytique-client/{id}',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/compte-analytique-client/compte-analytique-client-detail.html',
                                controller: 'CompteAnalytiqueClientDetailController',
                                controllerAs: 'vm'}
                        },
                        resolve: {
                        entity: ['$stateParams', 'CompteAnalytiqueClient', function ($stateParams, CompteAnalytiqueClient) {
                        return CompteAnalytiqueClient.get({id: $stateParams.id}).$promise;
                        }],
                                previousState: ["$state", function ($state) {
                                var currentStateData = {
                                name: $state.current.name || 'compte-analytique-client',
                                        params: $state.params,
                                        url: $state.href($state.current.name, $state.params)
                                };
                                        return currentStateData;
                                }]
                        }
                })
                .state('compte-analytique-client-detail.edit', {
                parent: 'compte-analytique-client-detail',
                        url: '/detail/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/compte-analytique-client/compte-analytique-client-dialog.html',
                                controller: 'CompteAnalytiqueClientDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CompteAnalytiqueClient', function (CompteAnalytiqueClient) {
                                return CompteAnalytiqueClient.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('compte-analytique-client.new', {
                parent: 'compte-analytique-client',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/compte-analytique-client/compte-analytique-client-dialog.html',
                                controller: 'CompteAnalytiqueClientDialogController',
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
                        $state.go('compte-analytique-client', null, {reload: 'compte-analytique-client'});
                        }, function () {
                        $state.go('compte-analytique-client');
                        });
                        }]
                })
                .state('compte-analytique-client.edit', {
                parent: 'compte-analytique-client',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/compte-analytique-client/compte-analytique-client-dialog.html',
                                controller: 'CompteAnalytiqueClientDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CompteAnalytiqueClient', function (CompteAnalytiqueClient) {
                                return CompteAnalytiqueClient.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('compte-analytique-client', null, {reload: 'compte-analytique-client'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('compte-analytique-client.delete', {
                parent: 'compte-analytique-client',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/compte-analytique-client/compte-analytique-client-delete-dialog.html',
                                controller: 'CompteAnalytiqueClientDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['CompteAnalytiqueClient', function (CompteAnalytiqueClient) {
                                return CompteAnalytiqueClient.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('compte-analytique-client', null, {reload: 'compte-analytique-client'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
