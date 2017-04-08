(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('activate', {
            parent: 'account',
            url: '/activate?key',
            data: {
                authorities: [],
                pageTitle: 'activate.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/activate/activate.html',
                    controller: 'ActivationController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
