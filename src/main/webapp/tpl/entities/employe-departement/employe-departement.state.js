(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('employe-departement', {
                parent: 'entity',
                        url: '/employe-departement?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/employe-departement/employe-departements.html',
                                controller: 'EmployeDepartementController',
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
                .state('employe-departement-detail', {
                parent: 'employe-departement',
                        url: '/employe-departement/{id}',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/employe-departement/employe-departement-detail.html',
                                controller: 'EmployeDepartementDetailController',
                                controllerAs: 'vm'}
                        },
                        resolve: {
                        entity: ['$stateParams', 'EmployeDepartement', function ($stateParams, EmployeDepartement) {
                        return EmployeDepartement.get({id: $stateParams.id}).$promise;
                        }],
                                previousState: ["$state", function ($state) {
                                var currentStateData = {
                                name: $state.current.name || 'employe-departement',
                                        params: $state.params,
                                        url: $state.href($state.current.name, $state.params)
                                };
                                        return currentStateData;
                                }]
                        }
                })
                .state('employe-departement-detail.edit', {
                parent: 'employe-departement-detail',
                        url: '/detail/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/employe-departement/employe-departement-dialog.html',
                                controller: 'EmployeDepartementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['EmployeDepartement', function (EmployeDepartement) {
                                return EmployeDepartement.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('employe-departement.new', {
                parent: 'employe-departement',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/employe-departement/employe-departement-dialog.html',
                                controller: 'EmployeDepartementDialogController',
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
                        $state.go('employe-departement', null, {reload: 'employe-departement'});
                        }, function () {
                        $state.go('employe-departement');
                        });
                        }]
                })
                .state('employe-departement.edit', {
                parent: 'employe-departement',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/employe-departement/employe-departement-dialog.html',
                                controller: 'EmployeDepartementDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['EmployeDepartement', function (EmployeDepartement) {
                                return EmployeDepartement.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('app.employe-departement', null, {reload: 'app.employe-departement'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('employe-departement.delete', {
                parent: 'employe-departement',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/employe-departement/employe-departement-delete-dialog.html',
                                controller: 'EmployeDepartementDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['EmployeDepartement', function (EmployeDepartement) {
                                return EmployeDepartement.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('app.employe-departement', null, {reload: 'app.employe-departement'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
