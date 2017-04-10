(function() {
    'use strict';
    angular
        .module('app')
        .factory('Decaissement', Decaissement);

    Decaissement.$inject = ['$resource'];

    function Decaissement ($resource) {
        var resourceUrl =  'api/decaissements/:id';

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
