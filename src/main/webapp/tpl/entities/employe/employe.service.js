(function() {
    'use strict';
    angular
        .module('app')
        .factory('Employe', Employe);

    Employe.$inject = ['$resource'];

    function Employe ($resource) {
        var resourceUrl =  'api/employes/:id';

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
