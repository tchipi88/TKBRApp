(function() {
    'use strict';
    angular
        .module('app')
        .factory('TerrainSuivi', TerrainSuivi);

    TerrainSuivi.$inject = ['$resource','DateUtils'];

    function TerrainSuivi ($resource,DateUtils) {
        var resourceUrl =  'api/terrain-suivis/:id';

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
