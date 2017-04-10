(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
                .state('access', {
                    parent: 'entity',
                    url: '/access?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },

                    templateUrl: 'tpl/entities/access/accesss.html',
                    controller: 'AccessController',
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
                .state('access-detail', {
                    parent: 'access',
                    url: '/access/{id}',
                    data: {
                        authorities: ['ROLE_USER']
                    },

                    templateUrl: 'tpl/entities/access/access-detail.html',
                    controller: 'AccessDetailController',
                    controllerAs: 'vm',

                    resolve: {
                        entity: ['$stateParams', 'Access', function ($stateParams, Access) {
                                return Access.get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'access',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('access-detail.edit', {
                    parent: 'access-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/access/access-dialog.html',
                                controller: 'AccessDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Access', function (Access) {
                                            return Access.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('access.new', {
                    parent: 'access',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/access/access-dialog.html',
                                controller: 'AccessDialogController',
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
                                $state.go('access', null, {reload: 'access'});
                            }, function () {
                                $state.go('access');
                            });
                        }]
                })
                .state('access.edit', {
                    parent: 'access',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/access/access-dialog.html',
                                controller: 'AccessDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['Access', function (Access) {
                                            return Access.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('app.access', null, {reload: 'app.access'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('access.delete', {
                    parent: 'access',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/access/access-delete-dialog.html',
                                controller: 'AccessDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['Access', function (Access) {
                                            return Access.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('app.access', null, {reload: 'app.access'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
