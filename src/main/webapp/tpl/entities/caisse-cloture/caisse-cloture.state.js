(function () {
'use strict';
        angular
        .module('app')
        .config(stateConfig);
        stateConfig.$inject = ['$stateProvider'];
        function stateConfig($stateProvider) {
        $stateProvider
                .state('caisse-cloture', {
                parent: 'entity',
                        url: '/caisse-cloture?page&sort&search',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/caisse-cloture/caisse-clotures.html',
                                controller: 'CaisseClotureController',
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
                .state('caisse-cloture-detail', {
                parent: 'caisse-cloture',
                        url: '/caisse-cloture/{id}',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        views: {
                        'content@app': {
                        templateUrl: 'tpl/entities/caisse-cloture/caisse-cloture-detail.html',
                                controller: 'CaisseClotureDetailController',
                                controllerAs: 'vm'}
                        },
                        resolve: {
                        entity: ['$stateParams', 'CaisseCloture', function ($stateParams, CaisseCloture) {
                        return CaisseCloture.get({id: $stateParams.id}).$promise;
                        }],
                                previousState: ["$state", function ($state) {
                                var currentStateData = {
                                name: $state.current.name || 'caisse-cloture',
                                        params: $state.params,
                                        url: $state.href($state.current.name, $state.params)
                                };
                                        return currentStateData;
                                }]
                        }
                })
                .state('caisse-cloture-detail.edit', {
                parent: 'caisse-cloture-detail',
                        url: '/detail/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-cloture/caisse-cloture-dialog.html',
                                controller: 'CaisseClotureDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CaisseCloture', function (CaisseCloture) {
                                return CaisseCloture.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('caisse-cloture.new', {
                parent: 'caisse-cloture',
                        url: '/new',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-cloture/caisse-cloture-dialog.html',
                                controller: 'CaisseClotureDialogController',
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
                        $state.go('caisse-cloture', null, {reload: 'caisse-cloture'});
                        }, function () {
                        $state.go('caisse-cloture');
                        });
                        }]
                })
                .state('caisse-cloture.edit', {
                parent: 'caisse-cloture',
                        url: '/{id}/edit',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-cloture/caisse-cloture-dialog.html',
                                controller: 'CaisseClotureDialogController',
                                controllerAs: 'vm',
                                backdrop: 'static',
                                size: 'lg',
                                resolve: {
                                entity: ['CaisseCloture', function (CaisseCloture) {
                                return CaisseCloture.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('caisse-cloture', null, {reload: 'caisse-cloture'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                })
                .state('caisse-cloture.delete', {
                parent: 'caisse-cloture',
                        url: '/{id}/delete',
                        data: {
                        authorities: ['ROLE_USER']
                        },
                        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'tpl/entities/caisse-cloture/caisse-cloture-delete-dialog.html',
                                controller: 'CaisseClotureDeleteController',
                                controllerAs: 'vm',
                                size: 'md',
                                resolve: {
                                entity: ['CaisseCloture', function (CaisseCloture) {
                                return CaisseCloture.get({id: $stateParams.id}).$promise;
                                }]
                                }
                        }).result.then(function () {
                        $state.go('caisse-cloture', null, {reload: 'caisse-cloture'});
                        }, function () {
                        $state.go('^');
                        });
                        }]
                });
        }

})();
