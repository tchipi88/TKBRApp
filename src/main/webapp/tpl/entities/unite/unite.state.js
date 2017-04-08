(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('unite', {
            parent: 'entity',
            url: '/unite',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tkbrApp.unite.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/unite/unites.html',
                    controller: 'UniteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('unite');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('unite-detail', {
            parent: 'unite',
            url: '/unite/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tkbrApp.unite.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/unite/unite-detail.html',
                    controller: 'UniteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('unite');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Unite', function($stateParams, Unite) {
                    return Unite.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'unite',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('unite-detail.edit', {
            parent: 'unite-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unite/unite-dialog.html',
                    controller: 'UniteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Unite', function(Unite) {
                            return Unite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('unite.new', {
            parent: 'unite',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unite/unite-dialog.html',
                    controller: 'UniteDialogController',
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
                    $state.go('unite', null, { reload: 'unite' });
                }, function() {
                    $state.go('unite');
                });
            }]
        })
        .state('unite.edit', {
            parent: 'unite',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unite/unite-dialog.html',
                    controller: 'UniteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Unite', function(Unite) {
                            return Unite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('unite', null, { reload: 'unite' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('unite.delete', {
            parent: 'unite',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/unite/unite-delete-dialog.html',
                    controller: 'UniteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Unite', function(Unite) {
                            return Unite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('unite', null, { reload: 'unite' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
