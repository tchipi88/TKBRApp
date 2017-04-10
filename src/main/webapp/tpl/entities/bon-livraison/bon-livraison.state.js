(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
                .state('bon-livraison', {
                    parent: 'entity',
                    url: '/bon-livraison?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },

                    templateUrl: 'tpl/entities/bon-livraison/bon-livraisons.html',
                    controller: 'BonLivraisonController',
                    controllerAs: 'vm',
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
                .state('bon-livraison-detail', {
                    parent: 'bon-livraison',
                    url: '/bon-livraison/{id}',
                    data: {
                        authorities: ['ROLE_USER']
                    },

                    templateUrl: 'tpl/entities/bon-livraison/bon-livraison-detail.html',
                    controller: 'BonLivraisonDetailController',
                    controllerAs: 'vm',

                    resolve: {
                        entity: ['$stateParams', 'BonLivraison', function ($stateParams, BonLivraison) {
                                return BonLivraison.get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'bon-livraison',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('bon-livraison-detail.edit', {
                    parent: 'bon-livraison-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/bon-livraison/bon-livraison-dialog.html',
                                controller: 'BonLivraisonDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['BonLivraison', function (BonLivraison) {
                                            return BonLivraison.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('bon-livraison.new', {
                    parent: 'bon-livraison',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/bon-livraison/bon-livraison-dialog.html',
                                controller: 'BonLivraisonDialogController',
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
                                $state.go('bon-livraison', null, {reload: 'bon-livraison'});
                            }, function () {
                                $state.go('bon-livraison');
                            });
                        }]
                })
                .state('bon-livraison.edit', {
                    parent: 'bon-livraison',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/bon-livraison/bon-livraison-dialog.html',
                                controller: 'BonLivraisonDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['BonLivraison', function (BonLivraison) {
                                            return BonLivraison.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('app.bon-livraison', null, {reload: 'app.bon-livraison'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('bon-livraison.delete', {
                    parent: 'bon-livraison',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/bon-livraison/bon-livraison-delete-dialog.html',
                                controller: 'BonLivraisonDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['BonLivraison', function (BonLivraison) {
                                            return BonLivraison.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('app.bon-livraison', null, {reload: 'app.bon-livraison'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
