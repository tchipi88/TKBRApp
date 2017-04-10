(function() {
    'use strict';
    angular
        .module('app')
        .factory('Encaissement', Encaissement);

    Encaissement.$inject = ['$resource'];

    function Encaissement ($resource) {
        var resourceUrl =  'api/encaissements/:id';

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
