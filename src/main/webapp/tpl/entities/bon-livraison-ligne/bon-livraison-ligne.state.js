(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
                .state('bon-livraison-ligne', {
                    parent: 'entity',
                    url: '/bon-livraison-ligne?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },

                    templateUrl: 'tpl/entities/bon-livraison-ligne/bon-livraison-lignes.html',
                    controller: 'BonLivraisonLigneController',
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
                .state('bon-livraison-ligne-detail', {
                    parent: 'bon-livraison-ligne',
                    url: '/bon-livraison-ligne/{id}',
                    data: {
                        authorities: ['ROLE_USER']
                    },

                    templateUrl: 'tpl/entities/bon-livraison-ligne/bon-livraison-ligne-detail.html',
                    controller: 'BonLivraisonLigneDetailController',
                    controllerAs: 'vm',

                    resolve: {
                        entity: ['$stateParams', 'BonLivraisonLigne', function ($stateParams, BonLivraisonLigne) {
                                return BonLivraisonLigne.get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'bon-livraison-ligne',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('bon-livraison-ligne-detail.edit', {
                    parent: 'bon-livraison-ligne-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/bon-livraison-ligne/bon-livraison-ligne-dialog.html',
                                controller: 'BonLivraisonLigneDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['BonLivraisonLigne', function (BonLivraisonLigne) {
                                            return BonLivraisonLigne.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('bon-livraison-ligne.new', {
                    parent: 'bon-livraison-ligne',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/bon-livraison-ligne/bon-livraison-ligne-dialog.html',
                                controller: 'BonLivraisonLigneDialogController',
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
                                $state.go('bon-livraison-ligne', null, {reload: 'bon-livraison-ligne'});
                            }, function () {
                                $state.go('bon-livraison-ligne');
                            });
                        }]
                })
                .state('bon-livraison-ligne.edit', {
                    parent: 'bon-livraison-ligne',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/bon-livraison-ligne/bon-livraison-ligne-dialog.html',
                                controller: 'BonLivraisonLigneDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['BonLivraisonLigne', function (BonLivraisonLigne) {
                                            return BonLivraisonLigne.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('app.bon-livraison-ligne', null, {reload: 'app.bon-livraison-ligne'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('bon-livraison-ligne.delete', {
                    parent: 'bon-livraison-ligne',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/bon-livraison-ligne/bon-livraison-ligne-delete-dialog.html',
                                controller: 'BonLivraisonLigneDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['BonLivraisonLigne', function (BonLivraisonLigne) {
                                            return BonLivraisonLigne.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('app.bon-livraison-ligne', null, {reload: 'app.bon-livraison-ligne'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
