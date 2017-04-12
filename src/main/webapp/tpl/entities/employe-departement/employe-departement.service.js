(function() {
    'use strict';
    angular
        .module('app')
        .factory('EmployeDepartement', EmployeDepartement);

    EmployeDepartement.$inject = ['$resource','DateUtils'];

    function EmployeDepartement ($resource,DateUtils) {
        var resourceUrl =  'api/employe-departements/:id';

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
