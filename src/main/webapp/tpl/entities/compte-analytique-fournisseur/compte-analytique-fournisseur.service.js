(function() {
    'use strict';
    angular
        .module('app')
        .factory('CompteAnalytiqueFournisseur', CompteAnalytiqueFournisseur);

    CompteAnalytiqueFournisseur.$inject = ['$resource','DateUtils'];

    function CompteAnalytiqueFournisseur ($resource,DateUtils) {
        var resourceUrl =  'api/compte-analytique-fournisseurs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
