(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('employe-fonction', {
            parent: 'entity',
            url: '/employe-fonction',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tkbrApp.employeFonction.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employe-fonction/employe-fonctions.html',
                    controller: 'EmployeFonctionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('employeFonction');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('employe-fonction-detail', {
            parent: 'employe-fonction',
            url: '/employe-fonction/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tkbrApp.employeFonction.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employe-fonction/employe-fonction-detail.html',
                    controller: 'EmployeFonctionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('employeFonction');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EmployeFonction', function($stateParams, EmployeFonction) {
                    return EmployeFonction.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'employe-fonction',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('employe-fonction-detail.edit', {
            parent: 'employe-fonction-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employe-fonction/employe-fonction-dialog.html',
                    controller: 'EmployeFonctionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmployeFonction', function(EmployeFonction) {
                            return EmployeFonction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employe-fonction.new', {
            parent: 'employe-fonction',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employe-fonction/employe-fonction-dialog.html',
                    controller: 'EmployeFonctionDialogController',
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
                    $state.go('employe-fonction', null, { reload: 'employe-fonction' });
                }, function() {
                    $state.go('employe-fonction');
                });
            }]
        })
        .state('employe-fonction.edit', {
            parent: 'employe-fonction',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employe-fonction/employe-fonction-dialog.html',
                    controller: 'EmployeFonctionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmployeFonction', function(EmployeFonction) {
                            return EmployeFonction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employe-fonction', null, { reload: 'employe-fonction' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employe-fonction.delete', {
            parent: 'employe-fonction',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employe-fonction/employe-fonction-delete-dialog.html',
                    controller: 'EmployeFonctionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EmployeFonction', function(EmployeFonction) {
                            return EmployeFonction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employe-fonction', null, { reload: 'employe-fonction' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
