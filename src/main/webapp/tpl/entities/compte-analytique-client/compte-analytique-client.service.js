(function() {
    'use strict';
    angular
        .module('app')
        .factory('CompteAnalytiqueClient', CompteAnalytiqueClient);

    CompteAnalytiqueClient.$inject = ['$resource','DateUtils'];

    function CompteAnalytiqueClient ($resource,DateUtils) {
        var resourceUrl =  'api/compte-analytique-clients/:id';

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
