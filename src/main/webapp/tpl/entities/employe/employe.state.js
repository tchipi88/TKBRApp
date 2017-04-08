(function() {
    'use strict';

    angular
        .module('tkbrApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('employe', {
            parent: 'entity',
            url: '/employe?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tkbrApp.employe.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employe/employes.html',
                    controller: 'EmployeController',
                    controllerAs: 'vm'
                }
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
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('employe');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('employe-detail', {
            parent: 'employe',
            url: '/employe/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tkbrApp.employe.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employe/employe-detail.html',
                    controller: 'EmployeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('employe');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Employe', function($stateParams, Employe) {
                    return Employe.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'employe',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('employe-detail.edit', {
            parent: 'employe-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employe/employe-dialog.html',
                    controller: 'EmployeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Employe', function(Employe) {
                            return Employe.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employe.new', {
            parent: 'employe',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employe/employe-dialog.html',
                    controller: 'EmployeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nom: null,
                                prenom: null,
                                dateNaissance: null,
                                lieuNaissance: null,
                                numeroCni: null,
                                telephone: null,
                                email: null,
                                adresse: null,
                                dateEntree: null,
                                salaire: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('employe', null, { reload: 'employe' });
                }, function() {
                    $state.go('employe');
                });
            }]
        })
        .state('employe.edit', {
            parent: 'employe',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employe/employe-dialog.html',
                    controller: 'EmployeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Employe', function(Employe) {
                            return Employe.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employe', null, { reload: 'employe' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employe.delete', {
            parent: 'employe',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employe/employe-delete-dialog.html',
                    controller: 'EmployeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Employe', function(Employe) {
                            return Employe.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employe', null, { reload: 'employe' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
