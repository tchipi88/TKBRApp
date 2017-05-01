(function() {
    'use strict';
    angular
        .module('app')
        .factory('Terrain', Terrain);

    Terrain.$inject = ['$resource','DateUtils'];

    function Terrain ($resource,DateUtils) {
        var resourceUrl =  'api/terrains/:id';

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
