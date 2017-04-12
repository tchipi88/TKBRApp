(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('bon-reception-ligne', {
                parent: 'entity',
                        url: '/bon-reception-ligne?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/bon-reception-ligne/bon-reception-lignes.html',
                                controller: 'BonReceptionLigneController',
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
                .state('bon-reception-ligne-detail', {
                parent: 'bon-reception-ligne',
                        url: '/bon-reception-ligne/{id}',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/bon-reception-ligne/bon-reception-ligne-detail.html',
                                controller: 'BonReceptionLigneDetailController',
                                controllerAs: 'vm'}
                        },
                        resolve: {
                        entity: ['$stateParams', 'BonReceptionLigne', function ($stateParams, BonReceptionLigne) {
                        return BonReceptionLigne.get({id: $stateParams.id}).$promise;
                        }],
                                previousState: ["$state", function ($state) {
                                var currentStateData = {
                                name: $state.current.name || 'bon-reception-ligne',
                                        params: $state.params,
                                        url: $state.href($state.current.name, $state.params)
                                };
                                        return currentStateData;
                                }]
                        }
                })
                .state('bon-reception-ligne-detail.edit', {
                parent: 'bon-reception-ligne-detail',
                        url: '/detail/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/bon-reception-ligne/bon-reception-ligne-dialog.html',
                                controller: 'BonReceptionLigneDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['BonReceptionLigne', function (BonReceptionLigne) {
                                return BonReceptionLigne.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('bon-reception-ligne.new', {
                parent: 'bon-reception-ligne',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/bon-reception-ligne/bon-reception-ligne-dialog.html',
                                controller: 'BonReceptionLigneDialogController',
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
                        $state.go('bon-reception-ligne', null, {reload: 'bon-reception-ligne'});
                        }, function () {
                        $state.go('bon-reception-ligne');
                        });
                        }]
                })
                .state('bon-reception-ligne.edit', {
                parent: 'bon-reception-ligne',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/bon-reception-ligne/bon-reception-ligne-dialog.html',
                                controller: 'BonReceptionLigneDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['BonReceptionLigne', function (BonReceptionLigne) {
                                return BonReceptionLigne.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('app.bon-reception-ligne', null, {reload: 'app.bon-reception-ligne'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('bon-reception-ligne.delete', {
                parent: 'bon-reception-ligne',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/bon-reception-ligne/bon-reception-ligne-delete-dialog.html',
                                controller: 'BonReceptionLigneDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['BonReceptionLigne', function (BonReceptionLigne) {
                                return BonReceptionLigne.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('app.bon-reception-ligne', null, {reload: 'app.bon-reception-ligne'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
