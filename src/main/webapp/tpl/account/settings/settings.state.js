(function () {
    'use strict';

    angular
            .module('app')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('settings', {
            parent: 'account',
            url: '/settings',
            data: {
                authorities: ['ROLE_USER'],
            },

            templateUrl: 'tpl/account/settings/settings.html',
            controller: 'SettingsController',
            controllerAs: 'vm'

        });
    }
})();
