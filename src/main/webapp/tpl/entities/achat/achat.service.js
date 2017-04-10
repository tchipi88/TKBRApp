(function() {
    'use strict';
    angular
        .module('app')
        .factory('Achat', Achat);

    Achat.$inject = ['$resource'];

    function Achat ($resource) {
        var resourceUrl =  'api/achats/:id';

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
