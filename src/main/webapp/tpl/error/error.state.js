(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
                .state('error', {
                    parent: 'app',
                    url: '/error',
                    data: {
                        authorities: [],
                        pageTitle: 'error.title'
                    },
                    templateUrl: 'tpl/error/error.html'
                })
               /* .state('accessdenied', { 
                    parent: 'app',
                    url: '/accessdenied',
                    data: {
                        authorities: []
                    },
                    templateUrl: 'tpl/error/accessdenied.html'
                });*/
                
                /*.state('accessdenied', {
                    parent: 'app',
                    url: '/accessdenied',
                    data: {
                        authorities: []
                    },
                    views: {
                        'content@': {
                            templateUrl: 'tpl/error/accessdenied.html'
                        }
                    },
                    resolve: {
                        translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('error');
                            return $translate.refresh();
                        }]
                    }
                });*/
                
                .state('accessdenied', {
                    parent: 'app',
                    url: '/accessdenied',
                    data: {
                        authorities: []
                    },
                    views: {
                        'content@app': {
                            templateUrl: 'tpl/error/accessdenied.html'
                        }
                    }
                });
                
    }
})();
