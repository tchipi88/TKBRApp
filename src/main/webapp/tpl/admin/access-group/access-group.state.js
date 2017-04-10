(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
                .state('access-group', {
                    parent: 'admin',
                    url: '/access-group?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },

                    templateUrl: 'tpl/admin/access-group/access-groups.html',
                    controller: 'AccessGroupController',
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
                .state('access-group-detail', {
                    parent: 'access-group',
                    url: '/access-group/{id}',
                    data: {
                        authorities: ['ROLE_USER']
                    },

                    templateUrl: 'tpl/admin/access-group/access-group-detail.html',
                    controller: 'AccessGroupDetailController',
                    controllerAs: 'vm',

                    resolve: {
                        entity: ['$stateParams', 'AccessGroup', function ($stateParams, AccessGroup) {
                                return AccessGroup.get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || 'access-group',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('access-group-detail.edit', {
                    parent: 'access-group-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/admin/access-group/access-group-dialog.html',
                                controller: 'AccessGroupDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['AccessGroup', function (AccessGroup) {
                                            return AccessGroup.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('access-group.new', {
                    parent: 'access-group',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/admin/access-group/access-group-dialog.html',
                                controller: 'AccessGroupDialogController',
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
                                $state.go('access-group', null, {reload: 'access-group'});
                            }, function () {
                                $state.go('access-group');
                            });
                        }]
                })
                .state('access-group.edit', {
                    parent: 'access-group',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/admin/access-group/access-group-dialog.html',
                                controller: 'AccessGroupDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['AccessGroup', function (AccessGroup) {
                                            return AccessGroup.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('app.access-group', null, {reload: 'app.access-group'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('access-group.delete', {
                    parent: 'access-group',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/admin/access-group/access-group-delete-dialog.html',
                                controller: 'AccessGroupDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['AccessGroup', function (AccessGroup) {
                                            return AccessGroup.get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('app.access-group', null, {reload: 'app.access-group'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
