(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
                .state('[(${entity_url})]', {
                    parent: 'entity',
                    url: '/[(${entity_url})]?page&sort&search',
                    data: {
                        authorities: ['ROLE_USER']
                    },

                    templateUrl: 'tpl/entities/[(${entity_url})]/[(${entity_url})]s.html',
                    controller: '[(${entity})]Controller',
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
                .state('[(${entity_url})]-detail', {
                    parent: '[(${entity_url})]',
                    url: '/[(${entity_url})]/{id}',
                    data: {
                        authorities: ['ROLE_USER']
                    },

                    templateUrl: 'tpl/entities/[(${entity_url})]/[(${entity_url})]-detail.html',
                    controller: '[(${entity})]DetailController',
                    controllerAs: 'vm',

                    resolve: {
                        entity: ['$stateParams', '[(${entity})]', function ($stateParams, [(${entity})]) {
                                return [(${entity})].get({id: $stateParams.id}).$promise;
                            }],
                        previousState: ["$state", function ($state) {
                                var currentStateData = {
                                    name: $state.current.name || '[(${entity_url})]',
                                    params: $state.params,
                                    url: $state.href($state.current.name, $state.params)
                                };
                                return currentStateData;
                            }]
                    }
                })
                .state('[(${entity_url})]-detail.edit', {
                    parent: '[(${entity_url})]-detail',
                    url: '/detail/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/[(${entity_url})]/[(${entity_url})]-dialog.html',
                                controller: '[(${entity})]DialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['[(${entity})]', function ([(${entity})]) {
                                            return [(${entity})].get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('^', {}, {reload: false});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('[(${entity_url})].new', {
                    parent: '[(${entity_url})]',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/[(${entity_url})]/[(${entity_url})]-dialog.html',
                                controller: '[(${entity})]DialogController',
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
                                $state.go('[(${entity_url})]', null, {reload: '[(${entity_url})]'});
                            }, function () {
                                $state.go('[(${entity_url})]');
                            });
                        }]
                })
                .state('[(${entity_url})].edit', {
                    parent: '[(${entity_url})]',
                    url: '/{id}/edit',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/[(${entity_url})]/[(${entity_url})]-dialog.html',
                                controller: '[(${entity})]DialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                    entity: ['[(${entity})]', function ([(${entity})]) {
                                            return [(${entity})].get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('app.[(${entity_url})]', null, {reload: 'app.[(${entity_url})]'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                })
                .state('[(${entity_url})].delete', {
                    parent: '[(${entity_url})]',
                    url: '/{id}/delete',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                            $uibModal.open({
                                templateUrl: 'tpl/entities/[(${entity_url})]/[(${entity_url})]-delete-dialog.html',
                                controller: '[(${entity})]DeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                    entity: ['[(${entity})]', function ([(${entity})]) {
                                            return [(${entity})].get({id: $stateParams.id}).$promise;
                                        }]
                                }
                            }).result.then(function () {
                                $state.go('app.[(${entity_url})]', null, {reload: 'app.[(${entity_url})]'});
                            }, function () {
                                $state.go('^');
                            });
                        }]
                });
    }

})();
