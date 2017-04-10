(function() {
    'use strict';
    angular
        .module('app')
        .factory('Reglement', Reglement);

    Reglement.$inject = ['$resource'];

    function Reglement ($resource) {
        var resourceUrl =  'api/reglements/:id';

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
