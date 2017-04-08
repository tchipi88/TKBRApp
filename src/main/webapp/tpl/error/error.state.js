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
                .state('accessdenied', {
                    parent: 'app',
                    url: '/accessdenied',
                    data: {
                        authorities: []
                    },
                    templateUrl: 'tpl/error/accessdenied.html'
                });
    }
})();
