'use strict';

/**
 * Config for the router
 */
angular.module('app')
        .run(
                ['$rootScope', '$state', '$stateParams',
                    function ($rootScope, $state, $stateParams) {
                        $rootScope.$state = $state;
                        $rootScope.$stateParams = $stateParams;
                    }
                ]
                )
        .config(
                ['$stateProvider', '$urlRouterProvider', 'JQ_CONFIG', 'MODULE_CONFIG',
                    function ($stateProvider, $urlRouterProvider, JQ_CONFIG, MODULE_CONFIG) {
                        var layout = "tpl/app.html";

                        $urlRouterProvider
                                .otherwise('/access/404');

                        $stateProvider
                                .state('app', {
                                    abstract: true,
                                    url: '/app',
                                    templateUrl: layout,
                                    resolve: {
                                        authorize: ['Auth',
                                            function (Auth) {
                                                return Auth.authorize();
                                            }
                                        ]
                                    }
                                })
                                .state('app.home', {
                                    url: '/home',
                                    templateUrl: 'tpl/blank.html',
                                    authenticate: true
                                });



                        function load(srcs, callback) {
                            return {
                                deps: ['$ocLazyLoad', '$q',
                                    function ($ocLazyLoad, $q) {
                                        var deferred = $q.defer();
                                        var promise = false;
                                        srcs = angular.isArray(srcs) ? srcs : srcs.split(/\s+/);
                                        if (!promise) {
                                            promise = deferred.promise;
                                        }
                                        angular.forEach(srcs, function (src) {
                                            promise = promise.then(function () {
                                                if (JQ_CONFIG[src]) {
                                                    return $ocLazyLoad.load(JQ_CONFIG[src]);
                                                }
                                                angular.forEach(MODULE_CONFIG, function (module) {
                                                    if (module.name == src) {
                                                        name = module.name;
                                                    } else {
                                                        name = src;
                                                    }
                                                });
                                                return $ocLazyLoad.load(name);
                                            });
                                        });
                                        deferred.resolve();
                                        return callback ? promise.then(function () {
                                            return callback();
                                        }) : promise;
                                    }]
                            }
                        }


                    }
                ]
                );
