(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('compte-analytique-fournisseur', {
                parent: 'entity',
                        url: '/compte-analytique-fournisseur?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/compte-analytique-fournisseur/compte-analytique-fournisseurs.html',
                                controller: 'CompteAnalytiqueFournisseurController',
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
                .state('compte-analytique-fournisseur-detail', {
                parent: 'compte-analytique-fournisseur',
                        url: '/compte-analytique-fournisseur/{id}',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/compte-analytique-fournisseur/compte-analytique-fournisseur-detail.html',
                                controller: 'CompteAnalytiqueFournisseurDetailController',
                                controllerAs: 'vm'}
                        },
                        resolve: {
                        entity: ['$stateParams', 'CompteAnalytiqueFournisseur', function ($stateParams, CompteAnalytiqueFournisseur) {
                        return CompteAnalytiqueFournisseur.get({id: $stateParams.id}).$promise;
                        }],
                                previousState: ["$state", function ($state) {
                                var currentStateData = {
                                name: $state.current.name || 'compte-analytique-fournisseur',
                                        params: $state.params,
                                        url: $state.href($state.current.name, $state.params)
                                };
                                        return currentStateData;
                                }]
                        }
                })
                .state('compte-analytique-fournisseur-detail.edit', {
                parent: 'compte-analytique-fournisseur-detail',
                        url: '/detail/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/compte-analytique-fournisseur/compte-analytique-fournisseur-dialog.html',
                                controller: 'CompteAnalytiqueFournisseurDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CompteAnalytiqueFournisseur', function (CompteAnalytiqueFournisseur) {
                                return CompteAnalytiqueFournisseur.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('compte-analytique-fournisseur.new', {
                parent: 'compte-analytique-fournisseur',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/compte-analytique-fournisseur/compte-analytique-fournisseur-dialog.html',
                                controller: 'CompteAnalytiqueFournisseurDialogController',
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
                        $state.go('compte-analytique-fournisseur', null, {reload: 'compte-analytique-fournisseur'});
                        }, function () {
                        $state.go('compte-analytique-fournisseur');
                        });
                        }]
                })
                .state('compte-analytique-fournisseur.edit', {
                parent: 'compte-analytique-fournisseur',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/compte-analytique-fournisseur/compte-analytique-fournisseur-dialog.html',
                                controller: 'CompteAnalytiqueFournisseurDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CompteAnalytiqueFournisseur', function (CompteAnalytiqueFournisseur) {
                                return CompteAnalytiqueFournisseur.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('compte-analytique-fournisseur', null, {reload: 'compte-analytique-fournisseur'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('compte-analytique-fournisseur.delete', {
                parent: 'compte-analytique-fournisseur',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/compte-analytique-fournisseur/compte-analytique-fournisseur-delete-dialog.html',
                                controller: 'CompteAnalytiqueFournisseurDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['CompteAnalytiqueFournisseur', function (CompteAnalytiqueFournisseur) {
                                return CompteAnalytiqueFournisseur.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('compte-analytique-fournisseur', null, {reload: 'compte-analytique-fournisseur'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
