(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('privilege', {
                parent: 'entity',
                        url: '/privilege?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/privilege/privileges.html',
                                controller: 'PrivilegeController',
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
                .state('privilege-detail', {
                parent: 'privilege',
                        url: '/privilege/{id}',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/privilege/privilege-detail.html',
                                controller: 'PrivilegeDetailController',
                                controllerAs: 'vm'}
                        },
                        resolve: {
                        entity: ['$stateParams', 'Privilege', function ($stateParams, Privilege) {
                        return Privilege.get({id: $stateParams.id}).$promise;
                        }],
                                previousState: ["$state", function ($state) {
                                var currentStateData = {
                                name: $state.current.name || 'privilege',
                                        params: $state.params,
                                        url: $state.href($state.current.name, $state.params)
                                };
                                        return currentStateData;
                                }]
                        }
                })
                .state('privilege-detail.edit', {
                parent: 'privilege-detail',
                        url: '/detail/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/privilege/privilege-dialog.html',
                                controller: 'PrivilegeDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['Privilege', function (Privilege) {
                                return Privilege.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('privilege.new', {
                parent: 'privilege',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/privilege/privilege-dialog.html',
                                controller: 'PrivilegeDialogController',
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
                        $state.go('privilege', null, {reload: 'privilege'});
                        }, function () {
                        $state.go('privilege');
                        });
                        }]
                })
                .state('privilege.edit', {
                parent: 'privilege',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/privilege/privilege-dialog.html',
                                controller: 'PrivilegeDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['Privilege', function (Privilege) {
                                return Privilege.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('privilege', null, {reload: 'privilege'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('privilege.delete', {
                parent: 'privilege',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/privilege/privilege-delete-dialog.html',
                                controller: 'PrivilegeDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['Privilege', function (Privilege) {
                                return Privilege.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('privilege', null, {reload: 'privilege'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
