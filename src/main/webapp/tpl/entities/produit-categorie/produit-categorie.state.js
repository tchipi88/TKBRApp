(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('produit-categorie', {
            parent: 'entity',
            url: '/produit-categorie',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tkbrApp.produitCategorie.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/produit-categorie/produit-categories.html',
                    controller: 'ProduitCategorieController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('produitCategorie');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('produit-categorie-detail', {
            parent: 'produit-categorie',
            url: '/produit-categorie/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tkbrApp.produitCategorie.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/produit-categorie/produit-categorie-detail.html',
                    controller: 'ProduitCategorieDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('produitCategorie');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProduitCategorie', function($stateParams, ProduitCategorie) {
                    return ProduitCategorie.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'produit-categorie',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('produit-categorie-detail.edit', {
            parent: 'produit-categorie-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produit-categorie/produit-categorie-dialog.html',
                    controller: 'ProduitCategorieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProduitCategorie', function(ProduitCategorie) {
                            return ProduitCategorie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('produit-categorie.new', {
            parent: 'produit-categorie',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produit-categorie/produit-categorie-dialog.html',
                    controller: 'ProduitCategorieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                libelle: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('produit-categorie', null, { reload: 'produit-categorie' });
                }, function() {
                    $state.go('produit-categorie');
                });
            }]
        })
        .state('produit-categorie.edit', {
            parent: 'produit-categorie',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produit-categorie/produit-categorie-dialog.html',
                    controller: 'ProduitCategorieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProduitCategorie', function(ProduitCategorie) {
                            return ProduitCategorie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('produit-categorie', null, { reload: 'produit-categorie' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('produit-categorie.delete', {
            parent: 'produit-categorie',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/produit-categorie/produit-categorie-delete-dialog.html',
                    controller: 'ProduitCategorieDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProduitCategorie', function(ProduitCategorie) {
                            return ProduitCategorie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('produit-categorie', null, { reload: 'produit-categorie' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
