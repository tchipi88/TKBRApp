(function() {
    'use strict';
    angular
        .module('tkbrApp')
        .factory('EmployeFonction', EmployeFonction);

    EmployeFonction.$inject = ['$resource'];

    function EmployeFonction ($resource) {
        var resourceUrl =  'api/employe-fonctions/:id';

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
