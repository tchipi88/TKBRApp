(function() {
    'use strict';
    angular
        .module('tkbrApp')
        .factory('Entrepot', Entrepot);

    Entrepot.$inject = ['$resource'];

    function Entrepot ($resource) {
        var resourceUrl =  'api/entrepots/:id';

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
