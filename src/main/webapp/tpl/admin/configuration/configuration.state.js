(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('jhi-configuration', {
            parent: 'admin',
            url: '/configuration',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            templateUrl: 'tpl/admin/configuration/configuration.html',
            controller: 'JhiConfigurationController',
            controllerAs: 'vm'

        });
    }
})();
